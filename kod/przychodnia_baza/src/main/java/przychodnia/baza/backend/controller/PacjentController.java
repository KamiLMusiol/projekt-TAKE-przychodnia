package przychodnia.baza.backend.controller;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import przychodnia.baza.backend.entity.Pacjent;
import przychodnia.baza.backend.repository.PacjentRepository;

@RestController
@RequestMapping("/pacjent")
public class PacjentController {

    @Autowired
    private PacjentRepository pacjentRepo;

    @PostMapping("/dodaj")
	public Pacjent dodaj(@RequestBody Pacjent pacjent)
	{
		return pacjentRepo.save(pacjent);
	}
	
	@GetMapping("/pobierz")
	public Iterable<Pacjent> pobierzWszystkie()
	{
		return pacjentRepo.findAll();
	}
	
	 @GetMapping("/pobierz/{id}")
	 public Pacjent pobierzPoId(@PathVariable("id") Integer id) 
	 {
	    return pacjentRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono pacjenta o id: " + id));
	 }

	 @DeleteMapping("/usun/{id}")
	    public void usun(@PathVariable Integer id) 
	    {
	    	pacjentRepo.deleteById(id);
	    }
	 
	 @PutMapping("/aktualizuj/{id}")
	 public Pacjent aktualizuj(@PathVariable Integer id, @RequestBody Pacjent nowyPacjent) { // id z url, @request body to obiekt requestu jako JSON, automatycznie zamienaiany na obiekt w javie (PCJENT)
	     return pacjentRepo.findById(id).map(pacjent -> { //szuka po id i jezeli znajdzie (czyli .map) to  dla pacjenta wybranego wykonaj
	         pacjent.setImie(nowyPacjent.getImie());
	         pacjent.setNazwisko(nowyPacjent.getNazwisko());
	         pacjent.setPesel(nowyPacjent.getPesel());
	         pacjent.setDataUrodzenia(nowyPacjent.getDataUrodzenia());
	         pacjent.setNumerTelefonu(nowyPacjent.getNumerTelefonu());
	         pacjent.setAdres(nowyPacjent.getAdres());
	         return pacjentRepo.save(pacjent); //zwraca i zapisuje do bazy	
	     }).orElseThrow(() -> new NoSuchElementException("Nie znaleziono pacjenta o id: " + id));
	 }	
	 
	 
	 @GetMapping("/szukaj")
	 public List<Pacjent> szukajPoNazwisku(@RequestParam("nazwisko") String nazwisko) {
	     return pacjentRepo.findByNazwisko(nazwisko);
	 }
	 //-----------------python-----------------
	 @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Pacjent> pacjenci) 
	 {
	        pacjentRepo.saveAll(pacjenci);
	 }
	 
}