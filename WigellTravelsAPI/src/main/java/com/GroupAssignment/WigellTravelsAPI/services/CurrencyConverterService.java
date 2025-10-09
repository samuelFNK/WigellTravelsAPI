package com.GroupAssignment.WigellTravelsAPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConverterService {

    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal convertSekToEuro(BigDecimal amountSek) {
        if (amountSek == null || amountSek.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        String url = "https://api.frankfurter.app/latest?from=SEK&to=EUR";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Object ratesObj = response.getBody().get("rates");
            if (!(ratesObj instanceof Map)) {
                throw new RuntimeException("Currency conversion failed: 'rates' missing");
            }

            Map<String, Object> rates = (Map<String, Object>) ratesObj;
            Object eurRate = rates.get("EUR");

            if (eurRate == null) {
                throw new RuntimeException("EUR rate not found in response");
            }

            BigDecimal rate = new BigDecimal(eurRate.toString());
            return amountSek.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new RuntimeException("Currency conversion failed: bad response");
        }
    }

}
