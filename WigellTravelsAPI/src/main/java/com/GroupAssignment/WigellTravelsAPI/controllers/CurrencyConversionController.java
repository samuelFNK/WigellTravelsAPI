package com.GroupAssignment.WigellTravelsAPI.controllers;

import com.GroupAssignment.WigellTravelsAPI.services.CurrencyConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wigelltravels/v1/currency")
public class CurrencyConversionController {

    public final CurrencyConverterService currencyConverterService;

    public CurrencyConversionController(CurrencyConverterService currencyConverterService){
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/sektoeur")
    public ResponseEntity<BigDecimal> convertSekToEur(@RequestParam BigDecimal amountSek){
        BigDecimal converted = currencyConverterService.convertSekToEuro(amountSek);
        return ResponseEntity.ok(converted);
    }

}
