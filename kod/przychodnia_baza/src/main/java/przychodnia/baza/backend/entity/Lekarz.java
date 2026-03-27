package przychodnia.baza.backend.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumn;


@Entity
@Getter                      
@Setter                      
@NoArgsConstructor
public class Lekarz {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String imie;
    private String nazwisko;
    private String numerPwz;
    private String email;
    
    
    @ManyToMany	
    @JoinTable(
        name = "lekarz_specjalizacja", // tabela pośrednia
        joinColumns = @JoinColumn(name = "lekarz_id"),
        inverseJoinColumns = @JoinColumn(name = "specjalizacja_id")
    )
    private List<Specjalizacja> specjalizacje;
    


}
