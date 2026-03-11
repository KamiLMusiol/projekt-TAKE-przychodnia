package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






// klasa jako kontroler interfejsu REST, będzie przyjmowac dane od klienta i konwertowac zwracane dane
@Controller 

//Przypisuje tej klasie główny adres URL. 
//Dzięki temu wszystkie żądania HTTP wysyłane pod ścieżkę /kredyt zostaną skierowane do tego konkretnego kontrolera.
@RequestMapping(path="/kredyt") 
@Validated
public class Controller_Class {
	
	
	
	
	
	//Spring samodzielnie wyszukuje odpowiedni obiekt w swoim kontenerze i automatycznie przypisuje go do deklarowanej zmiennej.
    @Autowired 
    Oprocentowanie oprocentowanie;
    
    //sprawdzenie czy wartosci sa wieksze od zera
    boolean czyPoprawneWartosci(double a, double b, double c)
    {
    	if( a<0 || b<0 || c<0 )
    		return false;
    	else 
    		return true;
    }
    
    // @GetMapping - adnotacja przypisana do metody, która określa, że dana metoda obsługuje żądania HTTP typu GET (czyli zapytania o pobranie danych)
    // @ResponseBody - Sprawia, że obiekt zwracany przez metodę jest wysyłany bezpośrednio do klienta w postaci tekstowej w ciele odpowiedzi (Response).
    // @RequestParam - Pobiera parametry przekazane w adresie URL żądania
    // @Validated - będziemy sprawdzać reguły przy pobieraniu 
    @GetMapping
    @ResponseBody 
    public String getKredyt(
    		@RequestParam(name = "kwota")  String kwota, 
            @RequestParam(name = "procent") String procent, 
            @RequestParam(name = "rat")  String rat)  {
    	
    	
    	try {
            
            double k = Double.parseDouble(kwota);
            double p = Double.parseDouble(procent);
            double r = Double.parseDouble(rat);
            
           
            
            if (!czyPoprawneWartosci(k, p, r)) {
                throw new ExceptionFromURL("Złe liczb, wszystkie wartości muszą być większe od zera.");
            }

            
            return "Wynik raty: " +  oprocentowanie.obliczRate(k, p, r);
            
        } catch (NumberFormatException e) {
        	
            return "Błąd, wprowadzone dane nie są liczbami";
            
        }catch (ExceptionFromURL e) {
           
            return "Błąd aplikacji: " + e.getMessage();
        }
    		
    	
       
    }
    
    
   
}

//http://localhost:8080/kredyt?kwota=10000&procent=0.1&rat=12 - do przeglądary