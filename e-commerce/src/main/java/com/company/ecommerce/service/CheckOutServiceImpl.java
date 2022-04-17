package com.company.ecommerce.service;

import com.company.ecommerce.dto.PaymentInfo;
import com.company.ecommerce.dto.Purchase;
import com.company.ecommerce.dto.PurchaseResponse;
import com.company.ecommerce.entity.Customer;
import com.company.ecommerce.entity.Order;
import com.company.ecommerce.entity.OrderItem;
import com.company.ecommerce.repository.CustomerRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CheckOutServiceImpl implements CheckOutService{
    private CustomerRepository customerRepository;

    public CheckOutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}")String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey=secretKey;
    }



    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
    //retrieve order info from dto.
        Order order=purchase.getOrder();

        String orderTrackingNumber=generateOrderTrackingNumber();

        order.setOrderTrackingNumber(orderTrackingNumber);
// getting order items into set
        Set<OrderItem> orderItems=purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));
        // populate them with billing address and shipping address.
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //Add order to the Customer.

        Customer customer =purchase.getCustomer();

        String email=customer.getEmail();

        Customer customerFromDb=customerRepository.findByEmail(email);

        if (customerFromDb!=null) {
            customer=customerFromDb;
        }

        customer.add(order);

        // save to the database
        customerRepository.save(customer);


        return new PurchaseResponse(orderTrackingNumber);
    }


    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
List<String> paymentMethodTypes=new ArrayList<>();
paymentMethodTypes.add("card");
        Map<String,Object>params=new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency",paymentInfo.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);
        return PaymentIntent.create(params);
    }


    private String generateOrderTrackingNumber(){
    //generating a random UUID number.
        return UUID.randomUUID().toString();
    }



}
