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
	 public Pacjent pobierzPoId(@PathVariable Integer id) 
	 {
	    return pacjentRepo.findById(id).orElse(null);
	 }

	 @DeleteMapping("/usun/{id}")
	    public void usun(@PathVariable Integer id) 
	    {
	    	pacjentRepo.deleteById(id);
	    }
	 
	 
	 //-----------------python-----------------
	 @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Pacjent> pacjenci) 
	 {
	        pacjentRepo.saveAll(pacjenci);
	 }
	 
}