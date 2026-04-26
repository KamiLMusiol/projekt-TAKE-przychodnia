package przychodnia.baza.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import przychodnia.baza.backend.entity.Choroby;

public interface ChorobyRepository  extends CrudRepository<Choroby, Integer>{
	
	List<Choroby> findByKodIcd10(String kodIcd10);

}
