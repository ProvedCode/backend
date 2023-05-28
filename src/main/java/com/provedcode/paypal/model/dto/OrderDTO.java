package com.provedcode.paypal.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.provedcode.paypal.model.OrderIntent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    OrderIntent intent;
    @JsonProperty("purchase_units")
    List<PurchaseUnit> purchaseUnits;
    @JsonProperty("application_context")
    PayPalAppContextDTO applicationContext;

    private record PurchaseUnit(Amount amount) {
    }

    private record Amount(@JsonProperty("currency_code") String currencyCode, Float value) {
    }
}
