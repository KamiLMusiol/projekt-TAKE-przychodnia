package przychodnia.baza.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import przychodnia.baza.backend.entity.Specjalizacja;
import przychodnia.baza.backend.repository.SpecjalizacjaRepository;


//specjalizacja to baza która będzie tylko do odczytu w jej środku będą już dawno zadeklarowane specjalizacje raczej pprzez plik sql
@RestController
@RequestMapping("/specjalizacja")
public class SpecjalizacjaController {

    @Autowired
    private SpecjalizacjaRepository specjalizacjaRepo;


    @GetMapping("/pobierz")
    public Iterable<Specjalizacja> pobierzWszystkie() 
    {
        return specjalizacjaRepo.findAll();
    }
    
    @GetMapping("/pobierz/{id}")
    public Specjalizacja pobierzPoId(@PathVariable Integer id)
    {
    	return specjalizacjaRepo.findById(id).orElse(null);
    }
}