package com.provedcode.paypal.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.provedcode.paypal.model.OrderStatus;
import com.provedcode.paypal.model.dto.OrderDTO;
import com.provedcode.paypal.model.dto.PayPalAppContextDTO;
import com.provedcode.paypal.model.entity.Order;
import com.provedcode.paypal.model.response.OrderResponse;
import com.provedcode.paypal.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PayPalService {
    PayPalHttpClient payPalHttpClient;
    OrderRepository orderRepository;

    public OrderResponse checkout(OrderDTO order) {
        PayPalAppContextDTO appContext = PayPalAppContextDTO.builder()
                .returnUrl("http://localhost:8080/api/v5/checkout/success").brandName("ProvedCode").build();
        order.setApplicationContext(appContext);
        try {
            OrderResponse response = payPalHttpClient.createOrder(order);
            orderRepository
                    .save(Order.builder().paypalOrderId(response.id()).paypalOrderStatus(response.status()).build());
            return response;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    public void approved(String orderId) {
        Order order = orderRepository.findByPaypalOrderId(orderId);
        order.setPaypalOrderStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
    }
}
