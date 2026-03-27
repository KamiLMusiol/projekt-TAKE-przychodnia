package przychodnia.baza.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter                      
@Setter                      
@NoArgsConstructor
public class Choroby {
	
	@Id
	@GeneratedValue
    private Integer id;

    private String nazwa;
    private String opis;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "choroby")
    private List<Wizyta> wizyta;
}
