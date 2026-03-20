package przychodnia.baza.backend;

import java.time.LocalDate;

import jakarta.persistence.Id;

public class Lekarz {
	
	@Id
	private Integer id;
	
	private String imie;
    private String nazwisko;
    private String numerPwz;
    private String email;
    
    //TODO zrobic nowa tabele dotyczącą specjalizacji
    


}
