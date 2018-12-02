package net.trexis.tipsy.controller;

import net.trexis.tipsy.exception.ApplicationJsonException;
import net.trexis.tipsy.service.Impl.NotificationService;
import net.trexis.tipsy.service.Impl.PayPalService;
import net.trexis.tipsy.status.Impl.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/system")
public class SystemController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    PayPalService payPalService;

    @RequestMapping(method = RequestMethod.POST, value = "/service/{serviceName}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> enableService(@PathVariable String serviceName, @RequestParam(value="enabled", required=true) Boolean enabled) throws ApplicationJsonException {
        try{
            switch (serviceName.toLowerCase()){
                case "notification": notificationService.setEnabled(enabled);
                case "paypal": payPalService.setEnabled(enabled);
            }
            return new ResponseEntity<String>(new SuccessResponse("Successfully updated service status").toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to enable or disable service.",ex);
        }
    }

}