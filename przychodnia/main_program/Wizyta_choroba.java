package com.przychodnia.backend;

import jakarta.persistence.*;
import lombok.*;


@Entity                     
@Getter @Setter            
@NoArgsConstructor         
@AllArgsConstructor
public class Wizyta_choroba {
	              
	//sztuczne id po prostu te zjebane srodowisko to wymaga
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
     
    //relacje
    @ManyToOne     //informacja ze bedzei polaczenia mnany to one        
    @JoinColumn(name = "wizyta_id") //tworzymy kolumne pacjent_id ktora referuje do id od Pacjenta
    private Wizyta wizyta; //mówi ze bierze tylko PK od pacjenta

    @ManyToOne             
    @JoinColumn(name = "choroba_id")
    private Choroba choroba;

}
