package przychodnia.baza.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import przychodnia.baza.backend.entity.Choroby;
import przychodnia.baza.backend.entity.Lekarz;
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
    
    @GetMapping("/pobierz/{id}")
	 public Choroby pobierzPoId(@PathVariable("id") Integer id) 
	 {
	    return chorobyRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono choroby o id: " + id));
	 }
    
    @PutMapping("/aktualizuj/{id}")
    public Choroby aktualizuj(@PathVariable Integer id, @RequestBody Choroby nowaChoroba) {
        return chorobyRepo.findById(id).map(choroba -> {
            choroba.setKodIcd10(nowaChoroba.getKodIcd10());
            choroba.setNazwa(nowaChoroba.getNazwa());
            choroba.setOpis(nowaChoroba.getOpis());
            return chorobyRepo.save(choroba);
        }).orElseThrow(() -> new NoSuchElementException("Nie znaleziono choroby o id: " + id));
    }

    @DeleteMapping("/usun/{id}")
    public void usun(@PathVariable Integer id) {
        chorobyRepo.deleteById(id);
    }
    
    @GetMapping("/szukaj")
    public List<Choroby> szukajPoKodzie(@RequestParam("kod") String kod) {
        return chorobyRepo.findByKodIcd10(kod);
    }
    
    
  //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Choroby> choroba) 
	 {
    	chorobyRepo.saveAll(choroba);
	 }
}