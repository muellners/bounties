package org.muellners.bounties.service.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StripeConfig {
    @Value("${application.stripe.publishable-key}")
    public String stripePublishableKey;

    @Value("${application.stripe.account-country}")
    public String stripeCountry;

    public String country;

    public String currency;

    @Value("${application.stripe.paymentMethods}")
    public List<String> paymentMethods;

    public StripeConfig() {
        this.stripeCountry = Optional.ofNullable(stripeCountry).orElse("US");
        this.country = "US";
        this.currency = "eur";
    }
}