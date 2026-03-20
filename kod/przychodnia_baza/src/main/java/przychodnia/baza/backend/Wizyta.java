package przychodnia.baza.backend;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

public class Wizyta {

	@Id
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "lekarz_id")
    private Lekarz lekarz;

    @ManyToOne
    @JoinColumn(name = "pacjent_id")
    private Pacjent pacjent;
    
    //TODO lepsze połączenie z chorobami
    
    //chwilowe połączenie 
    /*
    @ManyToMany
    @JoinTable(
        name = "wizyta_choroba",
        joinColumns = @JoinColumn(name = "wizyta_id"),
        inverseJoinColumns = @JoinColumn(name = "choroba_id")
    )
    private List<Choroba> choroby; // trzecia tablica łącząca
	
	*/
}
