package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Oprocentowanie implements Interfejs_Oprocentowanie {
	
	// z pdfa funkcja obliczająca rate
    @Override 
    public double obliczRate(double kwota, double procent, double rat) {
    	double m = 1 -1 / Math.pow(1.0 + procent/ 12, rat);
    	double rata = kwota* (procent/ 12) / m;
    	return rata;
    }
}