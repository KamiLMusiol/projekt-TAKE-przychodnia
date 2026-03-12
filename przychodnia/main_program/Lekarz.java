package com.przychodnia.backend;


import jakarta.persistence.*;
import lombok.*;




@Entity                     
@Getter @Setter            
@NoArgsConstructor         
@AllArgsConstructor
public class Lekarz {
	
	@Id                     
    private Integer id;

    private String nazwisko;
    private String imie;
    @Column(unique = true, nullable = false)
    private String numer_pwz;      // Numer Prawa Wykonywania Zawodu (unikalny)
    //TODO rozszerzyc specjalizacje na 2 osobne tablice to na dwie osobne tablice
    private String specjalizacja; 

    private String email;

}
