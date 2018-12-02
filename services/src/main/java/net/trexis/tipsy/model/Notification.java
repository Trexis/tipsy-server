package net.trexis.tipsy.model;

import net.trexis.tipsy.enums.NotificationType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashMap;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private NotificationType notificationType;
    private Long userId = null;
    private HashMap<String,String> notificationData;
    private Calendar initDate = Calendar.getInstance();
    private Calendar notifyDate = null;
    private boolean userNotified = false;
    private boolean userReceived = false;
    private String templateName = "default";

    public Notification(){

    }

    public Notification(Long userId, NotificationType notificationType, HashMap<String,String> notificationData, String templateName){
        this.userId = userId;
        this.notificationType = notificationType;
        this.notificationData = notificationData;
        this.templateName = templateName;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public HashMap<String, String> getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(HashMap<String, String> notificationData) {
        this.notificationData = notificationData;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public void setInitDate(Calendar initDate) {
        this.initDate = initDate;
    }

    public Calendar getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Calendar notifyDate) {
        this.notifyDate = notifyDate;
    }

    public boolean isUserNotified() {
        return userNotified;
    }

    public void setUserNotified(boolean userNotified) {
        this.userNotified = userNotified;
    }

    public boolean isUserReceived() {
        return userReceived;
    }

    public void setUserReceived(boolean userReceived) {
        this.userReceived = userReceived;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
