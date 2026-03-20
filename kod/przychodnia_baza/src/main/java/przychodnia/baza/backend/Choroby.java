package przychodnia.baza.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Choroby {
	
	   @Id
       private Integer id;

    private String nazwa;
    private String opis;

}
