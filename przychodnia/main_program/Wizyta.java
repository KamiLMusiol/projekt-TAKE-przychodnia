package com.przychodnia.backend;


import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


@Entity                     
@Getter @Setter            
@NoArgsConstructor         
@AllArgsConstructor
public class Wizyta {
	
	
	@Id                    
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataWizyty;

    private String status;
    private String nr_gabinetu;
    
    
    //relacje
    @ManyToOne     //informacja ze bedzei polaczenia mnany to one        
    @JoinColumn(name = "pacjent_id") //tworzymy kolumne pacjent_id ktora referuje do id od Pacjenta
    private Pacjent pacjent; //mówi ze bierze tylko PK od pacjenta

    @ManyToOne             
    @JoinColumn(name = "lekarz_id")
    private Lekarz lekarz;

}
