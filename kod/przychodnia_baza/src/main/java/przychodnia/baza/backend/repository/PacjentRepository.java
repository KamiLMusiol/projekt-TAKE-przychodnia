package przychodnia.baza.backend.repository;

import org.springframework.data.repository.CrudRepository;

import przychodnia.baza.backend.entity.Pacjent;

public interface PacjentRepository  extends CrudRepository<Pacjent, Integer>{

}