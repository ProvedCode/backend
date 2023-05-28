package com.provedcode.paypal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provedcode.paypal.model.dto.OrderDTO;
import com.provedcode.paypal.model.response.OrderResponse;
import com.provedcode.paypal.service.PayPalService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/v5")
@AllArgsConstructor
public class CheckoutController {
    PayPalService payPalService;

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    OrderResponse checkout(@RequestBody OrderDTO order) {
        return payPalService.checkout(order);
    }

    @GetMapping("/checkout/success")
    void paymentSuccess(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        payPalService.approved(orderId);
    }
}
