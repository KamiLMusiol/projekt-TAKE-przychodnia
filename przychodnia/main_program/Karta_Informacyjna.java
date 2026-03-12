package com.przychodnia.backend;

import jakarta.persistence.*;
import lombok.*;

@Entity                     
@Getter @Setter            
@NoArgsConstructor         
@AllArgsConstructor
public class Karta_Informacyjna {
	
	@Id
	private Long id;
	

	private String wywiad_lekarski;
	private String zalecenia;
	private String przypisane_leki;
	
	
	@OneToOne
    @JoinColumn(name = "wizyta_id")
    private Wizyta wizyta;

}
