package przychodnia.baza.backend.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import przychodnia.baza.backend.controller.WizytaController;
import przychodnia.baza.backend.entity.Wizyta;

public class WizytaDTO extends RepresentationModel<WizytaDTO> {

    public Integer id;
    public Integer nr_gabinetu;
    public LocalDate dataWizyty;
    public String zalecenia;
    public String status;

    public WizytaDTO(Wizyta wizyta) {
        this.id = wizyta.getId();
        this.nr_gabinetu = wizyta.getNr_gabinetu();
        this.dataWizyty = wizyta.getDataWizyty();
        this.zalecenia = wizyta.getZalecenia();
        this.status = wizyta.getStatus();

        this.add(Link.of("/wizyta/" + wizyta.getId() + "/lekarz").withRel("lekarz"));
        this.add(Link.of("/wizyta/" + wizyta.getId() + "/pacjent").withRel("pacjent"));
        this.add(Link.of("/wizyta/" + wizyta.getId() + "/choroby").withRel("choroby"));
    }
}