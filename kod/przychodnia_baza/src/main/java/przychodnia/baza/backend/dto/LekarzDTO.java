package przychodnia.baza.backend.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import przychodnia.baza.backend.controller.LekarzController;
import przychodnia.baza.backend.entity.Lekarz;

public class LekarzDTO extends RepresentationModel<LekarzDTO> {

    public Integer id;
    public String imie;
    public String nazwisko;
    public String numerPwz;
    public String email;

    public LekarzDTO(Lekarz lekarz) {
        this.id = lekarz.getId();
        this.imie = lekarz.getImie();
        this.nazwisko = lekarz.getNazwisko();
        this.numerPwz = lekarz.getNumerPwz();
        this.email = lekarz.getEmail();

        this.add(linkTo(methodOn(LekarzController.class)
            .pobierzSpecjalizacjeLekarza(lekarz.getId())).withRel("specjalizacje"));
    }
}