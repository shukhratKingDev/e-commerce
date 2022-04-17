package com.company.ecommerce.dto;

import com.company.ecommerce.entity.Address;
import com.company.ecommerce.entity.Customer;
import com.company.ecommerce.entity.Order;
import com.company.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
