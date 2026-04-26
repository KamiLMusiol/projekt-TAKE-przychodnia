package przychodnia.baza.backend.controller;

import przychodnia.baza.backend.dto.WizytaDTO;
import przychodnia.baza.backend.entity.Lekarz;
import przychodnia.baza.backend.entity.Pacjent;
import przychodnia.baza.backend.entity.Choroby;
import java.util.List;
import java.util.NoSuchElementException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.hateoas.CollectionModel;
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
    public CollectionModel<WizytaDTO> pobierzWszystkie() {
        List<WizytaDTO> lista = new ArrayList<>();
        for (Wizyta w : wizytaRepo.findAll())
            lista.add(new WizytaDTO(w));
        return CollectionModel.of(lista);
    }

    @DeleteMapping("/usun/{id}")
    public void usun(@PathVariable Integer id) 
    {
        wizytaRepo.deleteById(id);
    }
    
    @GetMapping("/pobierz/{id}")
    public WizytaDTO pobierzPoId(@PathVariable("id") Integer id) {
        Wizyta w = wizytaRepo.findById(id) .orElseThrow(() -> new NoSuchElementException("Nie znaleziono pacjenta o id: " + id));
        return new WizytaDTO(w);
    }
    @PutMapping("/aktualizuj/{id}")
    public Wizyta aktualizuj(@PathVariable Integer id, @RequestBody Wizyta nowaWizyta) {
        return wizytaRepo.findById(id).map(wizyta -> {
            wizyta.setNr_gabinetu(nowaWizyta.getNr_gabinetu());
            wizyta.setDataWizyty(nowaWizyta.getDataWizyty());
            wizyta.setZalecenia(nowaWizyta.getZalecenia());
            wizyta.setStatus(nowaWizyta.getStatus());
            wizyta.setLekarz(nowaWizyta.getLekarz());
            wizyta.setPacjent(nowaWizyta.getPacjent());
            wizyta.setChoroby(nowaWizyta.getChoroby());
            return wizytaRepo.save(wizyta);
        }).orElseThrow(() -> new NoSuchElementException("Nie znaleziono pacjenta o id: " + id));
        
        
    
    }
    
    @GetMapping("/{id}/lekarz")
    public Lekarz pobierzLekarzaWizyty(@PathVariable("id") Integer id) {
        return wizytaRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o id: " + id)).getLekarz();
    }

    @GetMapping("/{id}/pacjent")
    public Pacjent pobierzPacjentaWizyty(@PathVariable("id") Integer id) {
        return wizytaRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o id: " + id)).getPacjent();
    }

    @GetMapping("/{id}/choroby")
    public List<Choroby> pobierzChorobyWizyty(@PathVariable("id") Integer id) {
        return wizytaRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o id: " + id)).getChoroby();
    }
    
    @GetMapping("/szukaj/status")
    public List<Wizyta> szukajPoStatusie(@RequestParam("status") String status) {
        return wizytaRepo.findByStatus(status);
    }

    @GetMapping("/szukaj/data")
    public List<Wizyta> szukajPoDatacie(@RequestParam("data") LocalDate data) {
        return wizytaRepo.findByDataWizyty(data);
    }
    
    @GetMapping("/pacjent/{id}/choroby")
    public List<Object> historiaChorob(@PathVariable("id") Integer id) {
        return wizytaRepo.findChorobyByPacjentId(id);
    }
    
    @GetMapping("/lekarz/{id}/grafik")
    public List<Wizyta> grafikLekarza(@PathVariable("id") Integer id, 
                                       @RequestParam("od") LocalDate od, 
                                       @RequestParam("do") LocalDate do_) {
        return wizytaRepo.findGrafikLekarza(id, od, do_);
    }
    
    @GetMapping("/lekarz/{id}/pacjenci")
    public List<Object> pacjenciLekarza(@PathVariable("id") Integer id) {
        return wizytaRepo.findPacjenciByLekarzId(id);
    }
    
    
    @GetMapping("/gabinety")
    public List<Object[]> oblozanieGabinetow(@RequestParam("data") LocalDate data) {
        return wizytaRepo.findOblozanieGabinetow(data);
    }
    
    @GetMapping("/raport/icd10")
    public List<Object[]> raportIcd10(@RequestParam("miesiac") Integer miesiac, @RequestParam("rok") Integer rok) {
        return wizytaRepo.findRaportIcd10(miesiac, rok);
    }
    
    @GetMapping("/raport/pacjenci")
    public List<Object[]> pacjenciPoLiczbaWizyt(@RequestParam("rok") Integer rok, @RequestParam("min") Integer min) {
        return wizytaRepo.findPacjenciPoLiczbaWizyt(rok, min);
    }
    
    @GetMapping("/wolny-termin")
    public Boolean sprawdzWolnyTermin(@RequestParam("lekarzId") Integer lekarzId, @RequestParam("data") LocalDate data) {
        return wizytaRepo.isTerminWolny(lekarzId, data);
    }
    //-----------------python-----------------
    @PostMapping("/python")
	 public void dodajWielu(@RequestBody List<Wizyta> wizyta) 
	 {
    	wizytaRepo.saveAll(wizyta);
	 }
}