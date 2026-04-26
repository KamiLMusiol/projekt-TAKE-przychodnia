package przychodnia.baza.backend.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import przychodnia.baza.backend.entity.Lekarz;

public interface LekarzRepository  extends CrudRepository<Lekarz, Integer>{
	
	List<Lekarz> findByNazwisko(String nazwisko);

	
	@Query("SELECT l FROM Lekarz l JOIN l.specjalizacje s WHERE s.nazwa_specjalizacji = :nazwa")
	List<Lekarz> findBySpecjalizacja(@Param("nazwa") String nazwa);
}
