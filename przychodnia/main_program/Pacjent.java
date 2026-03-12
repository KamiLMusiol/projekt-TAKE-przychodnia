package com.przychodnia.backend;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity                     // Informuje JPA, że ta klasa to tabela w bazie danych [cite: 216]
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pacjent {
	
	@Id                 //pk  
    private Integer id;

   
    private String imie;
    private String nazwisko;
    @Column(unique = true, nullable = false, length = 11)
    private String pesel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_urodzenia;
    private String numer_telefonu;
    private String adres;

}
