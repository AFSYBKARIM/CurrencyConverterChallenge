package com.example.CurrencyConverter;
import com.example.CurrencyConverter.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        
    }

    @Test
    public void testGetRates() throws Exception {
       
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rates?base=USD"))
                .andExpect(status().isOk()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.USD").exists()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.EUR").exists()); 
    }

    @Test
    public void testConvertCurrency() throws Exception {
      
        String requestBody = "{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":100}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/convert")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.convertedAmount").value(94.5));  // Expect the correct conversion rate
    }
}


