package przychodnia.baza.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import przychodnia.baza.backend.entity.Choroby;
import przychodnia.baza.backend.entity.Specjalizacja;
import przychodnia.baza.backend.repository.ChorobyRepository;

@RestController
@RequestMapping("/choroba")
public class ChorobyController {

    @Autowired
    private ChorobyRepository chorobyRepo;

    @PostMapping("/dodaj")
    public Choroby dodaj(@RequestBody Choroby choroba) 
    {
        return chorobyRepo.save(choroba);
    }

    //Iterable to taka lista która zwraca ten CRUD
    @GetMapping("/pobierz")
    public Iterable<Choroby> pobierzWszystkie() 
    {
        return chorobyRepo.findAll();
    }
    
    
  //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Choroby> choroba) 
	 {
    	chorobyRepo.saveAll(choroba);
	 }
}