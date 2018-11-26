package net.trexis.tipsy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/funding")
public class Funding {

    @RequestMapping(method = RequestMethod.POST, value = "/createTip/{userId}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public double createTip(@PathVariable int userId, @RequestParam("tip") double tipAmount){
        double balance = 0;
        return balance;
    }
}
