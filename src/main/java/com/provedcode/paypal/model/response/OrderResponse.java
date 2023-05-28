package com.provedcode.paypal.model.response;

import java.util.List;

import com.provedcode.paypal.model.OrderStatus;

public record OrderResponse(String id, OrderStatus status, List<Link> links) {
    private record Link(String href, String rel, String method) {
    }
}
