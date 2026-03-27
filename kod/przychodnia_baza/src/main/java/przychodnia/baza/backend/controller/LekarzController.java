package przychodnia.baza.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public Iterable<Lekarz> pobierzWszystkie()
	{
		return lekarzRepo.findAll();
	}
	
	 @GetMapping("/pobierz/{id}")
	 public Lekarz pobierzPoId(@PathVariable Integer id) 
	 {
	    return lekarzRepo.findById(id).orElse(null);
	 }

  @DeleteMapping("/usun/{id}")
  public void usun(@PathVariable Integer id) 
  {
  lekarzRepo.deleteById(id);
  }

    //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Lekarz> lekarz) 
	 {
    	lekarzRepo.saveAll(lekarz);
	 }
 
}