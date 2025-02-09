package com.example.CurrencyConverter;

import com.example.CurrencyConverter.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

   
    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getRates(@RequestParam(defaultValue = "USD") String base) {
        try {
            Map<String, Double> rates = currencyService.getExchangeRates(base);
            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestBody ConversionResponse request) {
        try {
            double convertedAmount = currencyService.convertCurrency(request);
            ConversionResponse response = new ConversionResponse(
                request.getFrom(), 
                request.getTo(), 
                request.getAmount(), 
                convertedAmount
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
