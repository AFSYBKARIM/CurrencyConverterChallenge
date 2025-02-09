package com.example.CurrencyConverter;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import com.example.CurrencyConverter.ConversionResponse;

@Service
public class CurrencyService {

    
    private static final Map<String, Map<String, Double>> exchangeRates = new HashMap<>();

    static {
        Map<String, Double> usdRates = new HashMap<>();
        usdRates.put("EUR", 0.95); // 1 USD = 0.95 EUR
        usdRates.put("GBP", 0.83); // 1 USD = 0.83 GBP
        exchangeRates.put("USD", usdRates);

        Map<String, Double> eurRates = new HashMap<>();
        eurRates.put("USD", 1.05); // 1 EUR = 1.05 USD
        eurRates.put("GBP", 0.87); // 1 EUR = 0.87 GBP
        exchangeRates.put("EUR", eurRates);

        Map<String, Double> gbpRates = new HashMap<>();
        gbpRates.put("USD", 1.20); // 1 GBP = 1.20 USD
        gbpRates.put("EUR", 1.15); // 1 GBP = 1.15 EUR
        exchangeRates.put("GBP", gbpRates);
    }

    public Map<String, Double> getExchangeRates(String base) {
        // If no exchange rates are found for the base currency, return an empty map
        return exchangeRates.getOrDefault(base, new HashMap<>());
    }

  
    public double convertCurrency(ConversionResponse request) {
        Map<String, Double> rates = getExchangeRates(request.getFrom());
        Double rate = rates.get(request.getTo());
        if (rate != null) {
            return request.getAmount() * rate;
        } else {
            return 0.0; // return 0 if the conversion rate doesn't exist
        }
    }
}
