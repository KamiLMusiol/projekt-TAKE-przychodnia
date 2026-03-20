package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class OprocentowanieTest {

	
	Oprocentowanie serwis = new Oprocentowanie();

    @Test
    void testObliczen() {
    	
        // Test dla 10000 zl, 10%, 12 rat
        double wynik = serwis.obliczRate(10000.0, 0.1, 12.0);
        
        // Sprawdzamy, czy wynik jest zbliżony do oczekiwanego (ok. 879.16) z błędem zaokrąglenia 0.01         
        assertEquals(879.158, wynik, 0.01, "Wynik sie nie zgadza");
    }

   
}
