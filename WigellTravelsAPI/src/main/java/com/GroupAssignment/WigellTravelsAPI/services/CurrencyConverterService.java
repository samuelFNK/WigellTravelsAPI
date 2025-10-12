package com.GroupAssignment.WigellTravelsAPI.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyConverterService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LogManager.getLogger(CurrencyConverterService.class);


    public BigDecimal convertSekToEuro(BigDecimal amountSek) {
        if (amountSek == null || amountSek.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Currency conversion failed due to base amount being 0 || null");
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        String url = "https://api.frankfurter.app/latest?from=SEK&to=EUR";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Object ratesObj = response.getBody().get("rates");
            if (!(ratesObj instanceof Map)) {
                logger.warn("No conversion rate was located when requesting from the conversion API..");
                throw new RuntimeException("Currency conversion failed: 'rates' missing");
            }

            Map<String, Object> rates = (Map<String, Object>) ratesObj;
            Object eurRate = rates.get("EUR");

            if (eurRate == null) {
                logger.warn("The requested currency rate does not exist within conversion API.");
                throw new RuntimeException("EUR rate not found in response");
            }

            BigDecimal rate = new BigDecimal(eurRate.toString());
            logger.info("Successfully converted SEK to EUR.");
            return amountSek.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        } else {
            logger.warn("Failed to convert currency from SEK to EUR do to bad API response.");
            throw new RuntimeException("Currency conversion failed: bad response");
        }
    }

}
