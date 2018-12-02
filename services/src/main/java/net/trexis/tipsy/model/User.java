package net.trexis.tipsy.model;

import net.trexis.tipsy.enums.UserType;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"userType","emailAddress"})})
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Calendar dateCreated = Calendar.getInstance();
    private boolean enabled = true;
    private boolean active = false;
    private String activateCode = null;
    private Calendar dateActivateSent;
    private Calendar dateActivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public Calendar getDateActivateSent() {
        return dateActivateSent;
    }

    public void setDateActivateSent(Calendar dateActivateSent) {
        this.dateActivateSent = dateActivateSent;
    }

    public Calendar getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Calendar dateActivated) {
        this.dateActivated = dateActivated;
    }
}
