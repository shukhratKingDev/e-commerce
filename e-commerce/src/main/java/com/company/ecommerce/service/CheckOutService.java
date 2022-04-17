package com.company.ecommerce.service;

import com.company.ecommerce.dto.PaymentInfo;
import com.company.ecommerce.dto.Purchase;
import com.company.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckOutService {
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo)throws StripeException;

}
