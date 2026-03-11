package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //klasa jako kontroler interfejsu REST, będzie przyjmowac dane od klienta i konwertowac zwracane dane
@RequestMapping(path="/kredyt") //Przypisuje tej klasie główny adres URL. 
//Dzięki temu wszystkie żądania HTTP wysyłane pod ścieżkę /kredyt zostaną skierowane do tego konkretnego kontrolera.
public class Controller_Class {

    @Autowired //Spring samodzielnie wyszukuje odpowiedni obiekt w swoim kontenerze i automatycznie przypisuje go do deklarowanej zmiennej.
    Interfejs_Oprocentowanie oprocentowanie;

    @GetMapping //adnotacja przypisana do metody, która określa, że dana metoda obsługuje żądania HTTP typu GET (czyli zapytania o pobranie danych)
    //@ResponseBody: Sprawia, że obiekt zwracany przez metodę jest wysyłany bezpośrednio do klienta w postaci tekstowej w ciele odpowiedzi (Response).
    //@RequestParam: Pobiera parametry przekazane w adresie URL żądania
    public @ResponseBody Double getKredyt(
    		@RequestParam(name = "kwota") Double kwota, 
            @RequestParam(name = "procent") Double procent, 
            @RequestParam(name = "rat") Double rat)  {
        return oprocentowanie.obliczRate(kwota, procent, rat);
    }
}

//http://localhost:8080/kredyt?kwota=10000&procent=0.1&rat=12