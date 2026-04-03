package przychodnia.baza.backend.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter                      
@Setter                      
@NoArgsConstructor
public class Wizyta {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer nr_gabinetu;
	private LocalDate dataWizyty;
	private String zalecenia;
	private String status = "nieodbyty";
	 
	
	@ManyToOne
    @JoinColumn(name = "lekarz_id")
    private Lekarz lekarz;

	
	
    @ManyToOne
    @JoinColumn(name = "pacjent_id")
    private Pacjent pacjent;
    
  
    
    
    
    @ManyToMany
    private List<Choroby> choroby; // nie tylko jeden ale wiecej
	
}
