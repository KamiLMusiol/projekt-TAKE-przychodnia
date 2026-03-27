package przychodnia.baza.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import przychodnia.baza.backend.entity.Pacjent;
import przychodnia.baza.backend.entity.Wizyta;
import przychodnia.baza.backend.repository.WizytaRepository;

@RestController
@RequestMapping("/wizyta")
public class WizytaController {

    @Autowired
    private WizytaRepository wizytaRepo;

    @PostMapping("/dodaj")
    public Wizyta dodaj(@RequestBody Wizyta wizyta) 
    {
        return wizytaRepo.save(wizyta);
    }

    @GetMapping("/pobierz")
    public Iterable<Wizyta> pobierzWszystkie() 
    {
        return wizytaRepo.findAll();
    }

    @DeleteMapping("/usun/{id}")
    public void usun(@PathVariable Integer id) 
    {
        wizytaRepo.deleteById(id);
    }
    
    
    //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Wizyta> wizyta) 
	 {
    	wizytaRepo.saveAll(wizyta);
	 }
}