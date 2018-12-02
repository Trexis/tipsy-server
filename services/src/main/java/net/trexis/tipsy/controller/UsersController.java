package net.trexis.tipsy.controller;

import net.trexis.tipsy.enums.UserType;
import net.trexis.tipsy.enums.Validation;
import net.trexis.tipsy.exception.ApplicationJsonException;
import net.trexis.tipsy.model.User;
import net.trexis.tipsy.service.Impl.UsersService;
import net.trexis.tipsy.status.Impl.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(method = RequestMethod.POST, value = "/createTipper", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestParam(value="firstName", required=true) String firstName, @RequestParam(value="lastName", required=true) String lastName, @RequestParam(value="emailAddress", required=true) String emailAddress) throws ApplicationJsonException{
        try{
            usersService.createUser(UserType.tipper, firstName, lastName, emailAddress);
            return new ResponseEntity<String>(new SuccessResponse("Success").toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to create user.",ex);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> getUser(@RequestParam(value="emailAddress", required=true) String emailAddress) throws ApplicationJsonException {
        try{
            User user = usersService.getUserByEmail(emailAddress);
            return new ResponseEntity<String>(new SuccessResponse("Success", user).toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to get user.",ex);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activate", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> activateUser(@RequestParam(value="userId", required=true) Long userId, @RequestParam(value="code", required=true) String code) throws ApplicationJsonException {
        try{
            Validation validation = usersService.validateActivateCode(userId, code);
            if(validation.equals(Validation.valid)){
                usersService.activateUser(userId, true);
                return new ResponseEntity<String>(new SuccessResponse("Success", validation).toJson(), HttpStatus.OK);
            } else {
                if(validation.equals(Validation.expired)){
                    usersService.updateUserValidation(userId);
                    throw new Exception("Validation code expired. Sent a new code.");
                } else {
                    throw new Exception("Invalid activation information");
                }
            }
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to activate user.",ex);
        }
    }
}