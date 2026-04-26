package przychodnia.baza.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import przychodnia.baza.backend.entity.Wizyta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface WizytaRepository  extends CrudRepository<Wizyta, Integer>{
	List<Wizyta> findByStatus(String status);
	List<Wizyta> findByDataWizyty(LocalDate dataWizyty);
	

@Query("SELECT DISTINCT c FROM Wizyta w JOIN w.choroby c WHERE w.pacjent.id = :pacjentId")
List<Object> findChorobyByPacjentId(@Param("pacjentId") Integer pacjentId);


@Query("SELECT w FROM Wizyta w WHERE w.lekarz.id = :lekarzId AND w.dataWizyty BETWEEN :dataOd AND :dataDo")
List<Wizyta> findGrafikLekarza(@Param("lekarzId") Integer lekarzId, @Param("dataOd") LocalDate dataOd, @Param("dataDo") LocalDate dataDo);


@Query("SELECT DISTINCT w.pacjent FROM Wizyta w WHERE w.lekarz.id = :lekarzId")
List<Object> findPacjenciByLekarzId(@Param("lekarzId") Integer lekarzId);

@Query("SELECT w.nr_gabinetu, COUNT(w) FROM Wizyta w WHERE w.dataWizyty = :data GROUP BY w.nr_gabinetu")
List<Object[]> findOblozanieGabinetow(@Param("data") LocalDate data);

@Query("SELECT c.kodIcd10, COUNT(c) FROM Wizyta w JOIN w.choroby c WHERE MONTH(w.dataWizyty) = :miesiac AND YEAR(w.dataWizyty) = :rok GROUP BY c.kodIcd10")
List<Object[]> findRaportIcd10(@Param("miesiac") Integer miesiac, @Param("rok") Integer rok);


@Query("SELECT w.pacjent, COUNT(w) FROM Wizyta w WHERE YEAR(w.dataWizyty) = :rok GROUP BY w.pacjent HAVING COUNT(w) >= :minWizyt")
List<Object[]> findPacjenciPoLiczbaWizyt(@Param("rok") Integer rok, @Param("minWizyt") Integer minWizyt);

@Query("SELECT CASE WHEN COUNT(w) > 0 THEN false ELSE true END FROM Wizyta w WHERE w.lekarz.id = :lekarzId AND w.dataWizyty = :data")
Boolean isTerminWolny(@Param("lekarzId") Integer lekarzId, @Param("data") LocalDate data);

}






