package com.company.ecommerce.controller;

import com.company.ecommerce.dto.PaymentInfo;
import com.company.ecommerce.dto.Purchase;
import com.company.ecommerce.dto.PurchaseResponse;
import com.company.ecommerce.service.CheckOutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckOutController {
    private CheckOutService checkOutService;

    public CheckOutController(CheckOutService checkOutService) {
        this.checkOutService = checkOutService;
    }
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        return checkOutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String>createPaymentIntent(@RequestBody PaymentInfo paymentInfo)throws StripeException{
        PaymentIntent paymentIntent=checkOutService.createPaymentIntent(paymentInfo);
        String paymentStr=paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }


}
