package net.trexis.tipsy.service;

import net.trexis.tipsy.enums.UserType;
import net.trexis.tipsy.model.User;
import net.trexis.tipsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserByEmail(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress);
    }

    public void createUser(UserType userType, String firstName, String lastName, String emailAddress){
        User user = new User();
        user.setUserType(userType);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailAddress(emailAddress);
        userRepository.save(user);
    }
}
