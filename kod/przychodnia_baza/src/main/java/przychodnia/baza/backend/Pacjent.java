package przychodnia.baza.backend;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pacjent {
	
	@Id
	private Integer id;
	
	private String imie;
    private String nazwisko;
    private String pesel;
    private LocalDate dataUrodzenia;
    private String numerTelefonu;
    private String adres;

}
