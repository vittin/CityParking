package com.example.cityparking.customer;

import com.example.cityparking.config.BadCurrencyCodeException;
import com.example.cityparking.dao.model.ParkModel;
import com.example.cityparking.dao.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Currency;

@RestController
@RequestMapping("/api/v1/customer")
public class ParkingMeterCustomerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ParkingMeterCustomerService customerService;

    public ParkingMeterCustomerController(ParkingMeterCustomerService parkingMeterCustomerService) {
        this.customerService = parkingMeterCustomerService;
    }

    @GetMapping("/{customerIdentity}")
    ResponseEntity<Price> getAllParkings(@PathVariable String customerIdentity,
                                         @RequestParam(name = "currency", required = false, defaultValue = "PLN") String currencyCode){
        Currency currency = getCurrency(currencyCode);
        Price parkModel = customerService.checkFee(customerIdentity, currency);
        return ResponseEntity.ok(parkModel);
    }

    @PostMapping("/{customerIdentity}")
    ResponseEntity<ParkModel> startParking(@PathVariable String customerIdentity){
        ParkModel parkModel = customerService.start(customerIdentity);
        logger.info("customer {} started parking. Park event: {} ", customerIdentity, parkModel.getId());
        UriComponents path = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/park/{parkId}")
                .buildAndExpand(parkModel.getId());
        return ResponseEntity.created(path.toUri()).body(parkModel);
    }

    @GetMapping("/{customerIdentity}/park/{parkId}")
    ResponseEntity<Price> getParking(@PathVariable String customerIdentity,
                                     @PathVariable long parkId,
                                     @RequestParam(name = "currency", required = false, defaultValue = "PLN") String currencyCode){
        Currency currency = getCurrency(currencyCode);
        Price parkModel = customerService.checkFee(customerIdentity, currency, parkId);
        return ResponseEntity.ok(parkModel);
    }

    @PutMapping("/{customerIdentity}/park/{parkId}")
    ResponseEntity<ParkModel> stopParking(@PathVariable String customerIdentity,
                                     @PathVariable long parkId,
                                     @RequestParam(name = "currency", required = false, defaultValue = "PLN") String currencyCode){
        Currency currency = getCurrency(currencyCode);
        if (!customerService.makePayment(customerIdentity, parkId, currency)){
            throw new IllegalStateException("No money for payment");
        }
        ParkModel parkModel = customerService.stop(customerIdentity, parkId);
        return ResponseEntity.ok(parkModel);
    }

    private Currency getCurrency(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException illegalCurrencyCode){
            logger.info("Illegal currency code: {}", currencyCode);
            throw new BadCurrencyCodeException(currencyCode, illegalCurrencyCode);
        }
    }

}
