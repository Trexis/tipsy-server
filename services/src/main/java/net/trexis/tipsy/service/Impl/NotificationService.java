package net.trexis.tipsy.service.Impl;

import net.trexis.tipsy.enums.NotificationType;
import net.trexis.tipsy.model.Notification;
import net.trexis.tipsy.model.User;
import net.trexis.tipsy.repository.NotificationsRepository;
import net.trexis.tipsy.repository.UsersRepository;
import net.trexis.tipsy.service.TipsyService;
import net.trexis.tipsy.utilities.CommonUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@CacheConfig(cacheNames={"templates"})
public class NotificationService implements TipsyService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Value("${notification.enabled}")
    private boolean enabled;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public void notifyUser(Long userId, NotificationType notificationType, String message){
        HashMap<String,String> messageData = new HashMap<>();
        messageData.put("message", message);
        this.notifyUser(userId, notificationType, messageData, "default");
    }
    public void notifyUser(Long userId, NotificationType notificationType, HashMap<String,String> messageData, String templateName){
        Notification notification = new Notification(userId, notificationType, messageData==null? new HashMap<>() : messageData, templateName);
        notificationsRepository.save(notification);
    }

    public void notifyAllUsers(NotificationType notificationType, String message){
        HashMap<String,String> messageData = new HashMap<>();
        messageData.put("message", message);
        this.notifyAllUsers(notificationType, messageData, "default");
    }
    public void notifyAllUsers(NotificationType notificationType, HashMap<String,String> messageData, String templateName){
        Iterable<User> users = usersRepository.findAll();
        for (Iterator i = users.iterator(); i.hasNext(); ){
            User user = (User)i.next();
            Notification notification = new Notification(user.getId(), notificationType, messageData, templateName);
            notificationsRepository.save(notification);
        }
    }

    public void notifyAdmin(NotificationType notificationType, String message){
        HashMap<String,String> messageData = new HashMap<>();
        messageData.put("message", message);
        this.notifyAdmin(notificationType, messageData, "default");
    }
    public void notifyAdmin(NotificationType notificationType, HashMap<String,String> messageData, String templateName){
        Notification notification = new Notification(null, notificationType, messageData, templateName);
        notificationsRepository.save(notification);
    }


    public List<Notification> getUserNotifications(Long userId){
        return notificationsRepository.findUserNotifications(userId);
    }
    public List<Notification> getUserNotificationsByType(Long userId, NotificationType notificationType){
        return notificationsRepository.findUserNotificationsByType(userId, notificationType);
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Scheduled(cron = "${notification.email.cron}")
    public void notifyScheduler(){
        if(this.enabled) {
            try{
                List<Notification> emailNotificationsNotNotified = notificationsRepository.findNotificationsNotNotified(NotificationType.email);
                for (Notification notification : emailNotificationsNotNotified) {
                    User user = usersRepository.findByUserId(notification.getUserId());

                    extendMapWithUserInfo(notification.getNotificationData(), user);

                    String template = getTemplate(NotificationType.email, notification.getTemplateName());
                    template = personalizeTemplate(template, notification.getNotificationData());

                    String subject = getSubjectFromTemplate(template);
                    String toEmail = user.getEmailAddress();

                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    message.setSubject(subject);
                    helper.setTo(toEmail);
                    helper.setText(template, true);

                    mailSender.send(message);

                    logger.info("Sent email notification [" + notification.getId() + "] to " + toEmail);

                    notificationsRepository.setNotified(notification.getId(), Calendar.getInstance());
                }
            } catch (Exception ex){
                logger.error("Unable to send notification.", ex);
            }
        }
    }

    @Cacheable("templates")
    public String getTemplate(NotificationType notificationType, String templateName) throws IOException {
        String templateFolder = "/";
        switch (notificationType){
            case email: templateFolder = "/emailTemplates/";
        }

        InputStream inputStream = this.getClass().getResourceAsStream(templateFolder + templateName + ".html");
        String templateContent = CommonUtilities.readFromInputStream(inputStream);
        return templateContent;
    }

    private String personalizeTemplate(String templateContent, HashMap<String,String> mergeFields){
        String personalizedContent = templateContent;
        for(String key: mergeFields.keySet()){
            personalizedContent = personalizedContent.replaceAll("##" + key + "##", mergeFields.get(key));
        }
        return personalizedContent;
    }

    private void extendMapWithUserInfo(HashMap<String,String> notificationData, User user){
        notificationData.put("firstname", user.getFirstName());
        notificationData.put("lastname", user.getLastName());
        notificationData.put("emailaddress", user.getEmailAddress());
        notificationData.put("usertype", user.getUserType().toString());
    }

    private String getSubjectFromTemplate(String template){
        String subject = "";
        Pattern p = Pattern.compile("<head>.*?<title>(.*?)</title>.*?</head>", Pattern.DOTALL);
        Matcher m = p.matcher(template);
        while (m.find()) {
            subject = m.group(1);
        }
        return subject;
    }
}
