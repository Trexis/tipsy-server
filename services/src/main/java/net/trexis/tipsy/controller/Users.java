package net.trexis.tipsy.controller;

import net.trexis.tipsy.enums.UserType;
import net.trexis.tipsy.model.User;
import net.trexis.tipsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class Users {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/createTipper", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("emailAddress") String emailAddress){
        userService.createUser(UserType.Tipper, firstName, lastName, emailAddress);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestParam("emailAddress") String emailAddress){
        User user = userService.getUserByEmail(emailAddress);
        return user;
    }
}