package com.provedcode.paypal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record PayPalAppContextDTO(@JsonProperty("brand_name") String brandName,
        @JsonProperty("return_url") String returnUrl
// , @JsonProperty("cancel_url") String cancelUrl
) {
}
