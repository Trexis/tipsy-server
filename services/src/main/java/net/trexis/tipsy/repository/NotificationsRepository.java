package net.trexis.tipsy.repository;

import net.trexis.tipsy.enums.NotificationType;
import net.trexis.tipsy.model.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    @Query("select n from Notification n where n.userNotified = 0 and n.notificationType=:notificationType")
    List<Notification> findNotificationsNotNotified(@Param("notificationType") NotificationType notificationType);

    @Query("select n from Notification n where n.notificationType=:notificationType and userId=:userId")
    List<Notification> findUserNotificationsByType(@Param("userId") Long userId, @Param("notificationType") NotificationType notificationType);

    @Query("select n from Notification n where userId=:userId")
    List<Notification> findUserNotifications(@Param("userId")Long userId);

    @Modifying
    @Query("update Notification n set n.userNotified=1, n.notifyDate=:date where id=:id")
    void setNotified(@Param("id")Long id, @Param("date")Calendar calendar);


}