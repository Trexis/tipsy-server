package net.trexis.tipsy.service.Impl;

import net.trexis.tipsy.enums.NotificationType;
import net.trexis.tipsy.enums.UserType;
import net.trexis.tipsy.enums.Validation;
import net.trexis.tipsy.model.User;
import net.trexis.tipsy.repository.UsersRepository;
import net.trexis.tipsy.service.TipsyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsersService implements TipsyService {

    private boolean enabled;

    @Value("${url.service.public}")
    private String servicePublicUrl;
    @Value("${url.service.activateuser}")
    private String serviceActivateUrl;
    @Value("${url.web.public}")
    private String webPublicUrl;
    @Value("${url.web.profile}")
    private String webProfileUrl;
    @Value("${security.validationTimeoutPeriod}")
    private int timeoutPeriod;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    NotificationService notificationService;

    public User getUserByEmail(String emailAddress){
        return usersRepository.findByEmailAddress(emailAddress);
    }

    public void createUser(UserType userType, String firstName, String lastName, String emailAddress){
        User user = new User();
        user.setUserType(userType);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);
        User savedUser = usersRepository.save(user);
        updateUserValidation(savedUser);
    }

    public void activateUser(Long userId, boolean isActive){
        User user = usersRepository.findByUserId(userId);
        user.setActive(isActive);
        user.setDateActivated(Calendar.getInstance());
        usersRepository.save(user);

        HashMap<String,String> notificationData = new HashMap<>();
        notificationData.put("profileUrl", webPublicUrl + webProfileUrl);
        notificationService.notifyUser(user.getId(), NotificationType.email, notificationData, isActive ? "activate" : "deactivate");
    }

    public void updateUserValidation(Long userId){
        User user = usersRepository.findByUserId(userId);
        updateUserValidation(user);
    }
    public void updateUserValidation(User user){
        user.setActivateCode(UUID.randomUUID().toString());
        user.setDateActivateSent(Calendar.getInstance());
        usersRepository.save(user);

        HashMap<String,String> notificationData = new HashMap<>();
        notificationData.put("activateUrl", servicePublicUrl + serviceActivateUrl.replace("#userId#", user.getId().toString()).replace("#code#", user.getActivateCode()));
        notificationService.notifyUser(user.getId(), NotificationType.email, notificationData, "newuser");
    }

    public Validation validateActivateCode(Long userId, String activationCode){
        Validation validation = Validation.invalid;
        User user = usersRepository.findByUserId(userId);

        if (user.getActivateCode().equals(activationCode)) {
            validation = Validation.valid;
            if(user.getDateActivateSent()!=null) {
                Calendar now = Calendar.getInstance();
                long difference = now.getTimeInMillis() - user.getDateActivateSent().getTimeInMillis();
                if (difference > timeoutPeriod) {
                    validation = Validation.expired;
                }
            } else {
                validation = Validation.expired;
            }
        }


        return validation;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
