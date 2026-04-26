package przychodnia.baza.backend.repository;

import org.springframework.data.repository.CrudRepository;

import przychodnia.baza.backend.entity.Pacjent;
import java.util.List;


public interface PacjentRepository  extends CrudRepository<Pacjent, Integer>{
	
	List<Pacjent> findByNazwisko(String nazwisko);

}