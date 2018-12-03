package net.trexis.tipsy.controller;

import net.trexis.tipsy.exception.ApplicationJsonException;
import net.trexis.tipsy.status.Impl.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/funding")
public class FundingController {

    @RequestMapping(method = RequestMethod.POST, value = "/createTip/{userId}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> createTip(@PathVariable int userId, @RequestParam("tip") double tipAmount) throws ApplicationJsonException {
        try{
            double balance = 0;
            return new ResponseEntity<String>(new SuccessResponse("Success", balance).toJson(), HttpStatus.OK);
        } catch (Exception ex){
            throw new ApplicationJsonException("Unable to get escrow balance.",ex);
        }
    }
}
