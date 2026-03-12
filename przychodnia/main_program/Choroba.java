package com.przychodnia.backend;

import jakarta.persistence.*;
import lombok.*;


@Entity                     
@Getter @Setter            
@NoArgsConstructor         
@AllArgsConstructor
public class Choroba {

	
	@Id
	private Long id;
	
	@Column(unique = true)
	private String kod_icd;
	private String nazwa_choroby;
	private String opis;
}
