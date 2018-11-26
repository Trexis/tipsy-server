package net.trexis.tipsy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/escrow")
public class Escrow {

    @RequestMapping(method = RequestMethod.GET, value = "/balance", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public double getBalance(){
        double balance = 0;
        return balance;
    }
}
