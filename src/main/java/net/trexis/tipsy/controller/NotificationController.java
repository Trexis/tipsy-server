package net.trexis.tipsy.controller;

import net.trexis.tipsy.enums.NotificationType;
import net.trexis.tipsy.exception.ApplicationJsonException;
import net.trexis.tipsy.model.Notification;
import net.trexis.tipsy.service.Impl.NotificationService;
import net.trexis.tipsy.status.Impl.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping(method = RequestMethod.GET, value = "/", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> getAllUserNotifications(@RequestParam(value="userId", required=true) Long userId) throws ApplicationJsonException {
        try{
            List<Notification> notificationList = notificationService.getUserNotifications(userId);
            return new ResponseEntity<String>(new SuccessResponse("Success", notificationList).toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to get all user notifications.",ex);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{notificationType}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> getUserNotificationsByType(@PathVariable NotificationType notificationType, @RequestParam(value="userId", required=true) Long userId) throws ApplicationJsonException{
        try{
            List<Notification> notificationList = notificationService.getUserNotificationsByType(userId, notificationType);
            return new ResponseEntity<String>(new SuccessResponse("Success", notificationList).toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to get user notifications for " + notificationType.toString() + ".",ex);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{notificationType}/send", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> createUser(@PathVariable NotificationType notificationType, @RequestParam(value="userId", required=true) Long userId, @RequestParam(value="message", required=true) String message) throws ApplicationJsonException{
        try{
            notificationService.notifyUser(userId, notificationType, message);
            return new ResponseEntity<String>(new SuccessResponse("Success queued notification").toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to notify user.",ex);
        }
    }
}