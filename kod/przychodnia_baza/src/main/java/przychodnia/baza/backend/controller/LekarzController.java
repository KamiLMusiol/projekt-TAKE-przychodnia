package przychodnia.baza.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

//nowy import
import przychodnia.baza.backend.dto.LekarzDTO;
import przychodnia.baza.backend.entity.Specjalizacja;
import org.springframework.hateoas.CollectionModel;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import przychodnia.baza.backend.entity.Lekarz;
import przychodnia.baza.backend.entity.Pacjent;
import przychodnia.baza.backend.entity.Wizyta;
import przychodnia.baza.backend.repository.LekarzRepository;


@RestController
@RequestMapping("/lekarz")
public class LekarzController {
	
	@Autowired
	private LekarzRepository lekarzRepo;
	
	@PostMapping("/dodaj")
	public Lekarz dodaj(@RequestBody Lekarz lekarz)
	{
		return lekarzRepo.save(lekarz);
	}
	
	
	@GetMapping("/pobierz")
	public CollectionModel<LekarzDTO> pobierzWszystkie() {
	    List<LekarzDTO> lista = new ArrayList<>();
	    for (Lekarz l : lekarzRepo.findAll())
	        lista.add(new LekarzDTO(l));
	    return CollectionModel.of(lista);
	}
	
	@GetMapping("/pobierz/{id}")
	public LekarzDTO pobierzPoId(@PathVariable("id") Integer id) {
	    Lekarz l = lekarzRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono lekarza o id: " + id));
	    return new LekarzDTO(l);
	}
	
	@GetMapping("/{id}/specjalizacje")
	public List<Specjalizacja> pobierzSpecjalizacjeLekarza(@PathVariable("id") Integer id) {
	    return lekarzRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono lekarza o id: " + id)).getSpecjalizacje();
	}

  @DeleteMapping("/usun/{id}")
  public void usun(@PathVariable Integer id) 
  {
  lekarzRepo.deleteById(id);
  }
  
  @PutMapping("/aktualizuj/{id}")
  public Lekarz aktualizuj(@PathVariable Integer id, @RequestBody Lekarz nowyLekarz) {
      return lekarzRepo.findById(id).map(lekarz -> {
          lekarz.setImie(nowyLekarz.getImie());
          lekarz.setNazwisko(nowyLekarz.getNazwisko());
          lekarz.setNumerPwz(nowyLekarz.getNumerPwz());
          lekarz.setEmail(nowyLekarz.getEmail());
          lekarz.setSpecjalizacje(nowyLekarz.getSpecjalizacje());
          return lekarzRepo.save(lekarz);
      }).orElse(null);
  }

  @GetMapping("/szukaj")
  public List<Lekarz> szukajPoNazwisku(@RequestParam("nazwisko") String nazwisko) {
      return lekarzRepo.findByNazwisko(nazwisko);
  }
  
  
  @GetMapping("/specjalizacja")
  public List<Lekarz> szukajPoSpecjalizacji(@RequestParam("nazwa") String nazwa) {
      return lekarzRepo.findBySpecjalizacja(nazwa);
  }
  
    //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Lekarz> lekarz) 
	 {
    	lekarzRepo.saveAll(lekarz);
	 }
 
}