package przychodnia.baza.backend.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter                      
@Setter                      
@NoArgsConstructor
public class Pacjent {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String imie;
    private String nazwisko;
    private String pesel;
    private LocalDate dataUrodzenia;
    private String numerTelefonu;
    private String adres;

}
