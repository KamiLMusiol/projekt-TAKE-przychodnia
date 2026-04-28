# Przychodnia Baza — REST API

Backend systemu zarządzania przychodnią lekarską zbudowany w **Spring Boot**. Umożliwia zarządzanie pacjentami, lekarzami, wizytami, chorobami i specjalizacjami poprzez REST API.

---

## Technologie

- **Java 25** + **Spring Boot 4.0.4**
- **Spring Data JPA** + **Hibernate** — warstwa bazy danych
- **H2** — baza danych w pamięci (resetuje się przy każdym restarcie)
- **Spring HATEOAS** — linki do powiązanych zasobów
- **Springdoc OpenAPI (Swagger)** — automatyczna dokumentacja API
- **Lombok** — automatyczne generowanie getterów, setterów, konstruktorów

---

## Uruchomienie

1. Sklonuj repozytorium
2. Otwórz projekt w **Spring Tools Suite** lub **IntelliJ IDEA**
3. Uruchom klasę `PrzychodniaBazaApplication.java`
4. Serwer startuje na `http://localhost:8080`
5. Przy starcie automatycznie uruchamia się zewnętrzny skrypt (`plik_laczacy.exe` / `plik_laczacy`) który zasila bazę danymi testowymi

### Swagger UI
Po uruchomieniu dostępny pod:
```
http://localhost:8080/swagger-ui/index.html
```

---

## Architektura projektu

```
przychodnia.baza.backend/
├── entity/          # Encje JPA (tabele w bazie danych)
├── repository/      # Interfejsy dostępu do bazy danych
├── controller/      # Kontrolery REST API (endpointy)
├── dto/             # Obiekty transferu danych z linkami HATEOAS
└── config/          # Konfiguracja: obsługa błędów, generator danych
```

Projekt jest zbudowany w klasycznej architekturze **trójwarstwowej**:
- **Kontroler** odbiera request HTTP → przekazuje do **Repozytorium** → zwraca dane jako JSON

---

## Warstwa encji (entity/)

Encje to klasy Java mapowane na tabele w bazie danych. Każda encja ma adnotację `@Entity` i pole `@Id` jako klucz główny.

### Pacjent
Reprezentuje pacjenta w systemie.
```java
@Entity
public class Pacjent {
    @Id @GeneratedValue
    private Integer id;
    private String imie;
    private String nazwisko;
    private String pesel;
    private LocalDate dataUrodzenia;
    private String numerTelefonu;
    private String adres;
}
```

### Lekarz
Lekarz posiada relację **ManyToMany** ze specjalizacjami — jeden lekarz może mieć wiele specjalizacji, jedna specjalizacja może należeć do wielu lekarzy. Tabela pośrednia to `lekarz_specjalizacja`.
```java
@ManyToMany
@JoinTable(name = "lekarz_specjalizacja",
    joinColumns = @JoinColumn(name = "lekarz_id"),
    inverseJoinColumns = @JoinColumn(name = "specjalizacja_id"))
private List<Specjalizacja> specjalizacje;
```

### Wizyta
Centralna encja systemu — łączy lekarza z pacjentem i listą chorób.
- **ManyToOne** z `Lekarz` — wizyta ma jednego lekarza
- **ManyToOne** z `Pacjent` — wizyta ma jednego pacjenta
- **ManyToMany** z `Choroby` — wizyta może mieć wiele chorób
```java
@ManyToOne
@JoinColumn(name = "lekarz_id")
private Lekarz lekarz;

@ManyToOne
@JoinColumn(name = "pacjent_id")
private Pacjent pacjent;

@ManyToMany
private List<Choroby> choroby;
```
Domyślny status wizyty to `"nieodbyty"`.

### Choroby
Katalog chorób z kodem ICD-10. Relacja z wizytą jest dwukierunkowa — po stronie `Choroby` jest `mappedBy` co oznacza że to `Wizyta` zarządza tabelą pośrednią. `@JsonIgnore` zapobiega nieskończonej pętli przy serializacji do JSON.

### Specjalizacja
Słownik specjalizacji lekarskich. Działa analogicznie do `Choroby` — relacja zarządzana po stronie `Lekarz`.

---

## Warstwa repozytorium (repository/)

Repozytoria dziedziczą po `CrudRepository` co daje gotowe metody: `save()`, `findAll()`, `findById()`, `deleteById()` bez pisania SQLa.

### Zapytania niestandardowe
Dla bardziej złożonych zapytań używamy adnotacji `@Query` z językiem JPQL (obiektowy SQL):

```java
// Wszystkie choroby pacjenta (przechodząc przez wizyty)
@Query("SELECT DISTINCT c FROM Wizyta w JOIN w.choroby c WHERE w.pacjent.id = :pacjentId")
List<Object> findChorobyByPacjentId(@Param("pacjentId") Integer pacjentId);

// Wizyty lekarza w zakresie dat
@Query("SELECT w FROM Wizyta w WHERE w.lekarz.id = :lekarzId AND w.dataWizyty BETWEEN :dataOd AND :dataDo")
List<Wizyta> findGrafikLekarza(@Param("lekarzId") Integer lekarzId, 
                                @Param("dataOd") LocalDate dataOd, 
                                @Param("dataDo") LocalDate dataDo);

// Lekarze po nazwie specjalizacji
@Query("SELECT l FROM Lekarz l JOIN l.specjalizacje s WHERE s.nazwa_specjalizacji = :nazwa")
List<Lekarz> findBySpecjalizacja(@Param("nazwa") String nazwa);
```

Proste wyszukiwania po polach encji realizowane są przez konwencję nazewniczą (`findBy...`):
```java
List<Pacjent> findByNazwisko(String nazwisko);
List<Wizyta> findByStatus(String status);
List<Wizyta> findByDataWizyty(LocalDate dataWizyty);
```
Spring automatycznie generuje zapytanie SQL na podstawie nazwy metody.

---

## Warstwa kontrolerów (controller/)

Kontrolery obsługują requesty HTTP. Każdy kontroler ma adnotację `@RestController` i `@RequestMapping` określający bazowy URL.

### Struktura endpointów (przykład dla Pacjent)

| Metoda | URL | Opis |
|--------|-----|------|
| POST | `/pacjent/dodaj` | Dodanie nowego pacjenta |
| GET | `/pacjent/pobierz` | Pobranie wszystkich pacjentów |
| GET | `/pacjent/pobierz/{id}` | Pobranie pacjenta po ID |
| PUT | `/pacjent/aktualizuj/{id}` | Aktualizacja pacjenta |
| DELETE | `/pacjent/usun/{id}` | Usunięcie pacjenta |
| GET | `/pacjent/szukaj?nazwisko=X` | Wyszukiwanie po nazwisku |

### Jak działa metoda PUT (aktualizacja)
```java
@PutMapping("/aktualizuj/{id}")
public Pacjent aktualizuj(@PathVariable("id") Integer id, @RequestBody Pacjent nowyPacjent) {
    return pacjentRepo.findById(id)           // 1. znajdź istniejący rekord
        .map(pacjent -> {                      // 2. jeśli istnieje, nadpisz pola
            pacjent.setImie(nowyPacjent.getImie());
            // ...
            return pacjentRepo.save(pacjent);  // 3. zapisz (Hibernate robi UPDATE)
        })
        .orElseThrow(() -> new NoSuchElementException("Nie znaleziono pacjenta o id: " + id));
}
```
- `@PathVariable("id")` — pobiera ID z URLa np. `/aktualizuj/5` → `id = 5`
- `@RequestBody` — deserializuje JSON z ciała requestu na obiekt Java
- `.map()` — wykonuje kod tylko jeśli obiekt istnieje w bazie
- `.orElseThrow()` — rzuca wyjątek 404 jeśli nie znaleziono

### Masowe dodawanie danych (endpoint /python)
Każdy kontroler posiada endpoint `/python` do masowego importu danych przez zewnętrzny skrypt:
```java
@PostMapping("/python")
public void dodajWielu(@RequestBody List<Pacjent> pacjenci) {
    pacjentRepo.saveAll(pacjenci);
}
```

---

## Warstwa DTO i HATEOAS (dto/)

Zamiast zwracać surowe encje z zagnieżdżonymi obiektami (embedded), używamy wzorca **HATEOAS** — zwracamy linki do powiązanych zasobów.

### Przykład — WizytaDTO
```java
public class WizytaDTO extends RepresentationModel<WizytaDTO> {
    public Integer id;
    public LocalDate dataWizyty;
    // ...

    public WizytaDTO(Wizyta wizyta) {
        this.id = wizyta.getId();
        // zamiast wklejać obiekt lekarza — dajemy link:
        this.add(Link.of("/wizyta/" + wizyta.getId() + "/lekarz").withRel("lekarz"));
        this.add(Link.of("/wizyta/" + wizyta.getId() + "/pacjent").withRel("pacjent"));
        this.add(Link.of("/wizyta/" + wizyta.getId() + "/choroby").withRel("choroby"));
    }
}
```

### Przykład odpowiedzi z HATEOAS
```json
{
    "id": 1,
    "dataWizyty": "2025-08-19",
    "status": "nieodbyty",
    "_links": {
        "lekarz":  { "href": "/wizyta/1/lekarz" },
        "pacjent": { "href": "/wizyta/1/pacjent" },
        "choroby": { "href": "/wizyta/1/choroby" }
    }
}
```
Klient sam decyduje czy potrzebuje danych lekarza — jeśli tak, robi osobny request pod link.

---

## Obsługa błędów (config/GlobalExceptionHandler.java)

Klasa z adnotacją `@RestControllerAdvice` przechwytuje wyjątki rzucane przez kontrolery i zamienia je na czytelne odpowiedzi JSON zamiast domyślnych stacktrace'ów.

```java
@ExceptionHandler(NoSuchElementException.class)
public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
    // zwraca HTTP 404 z JSON-em opisującym błąd
}
```

### Przykład odpowiedzi błędu
Request: `GET /pacjent/pobierz/999`
```json
{
    "status": 404,
    "error": "Nie znaleziono",
    "message": "Nie znaleziono pacjenta o id: 999",
    "timestamp": "2026-04-26T10:39:35"
}
```

---

## Zasilanie bazy danych (config/GeneratorDanychPrzedUruhomieniem.java)

Klasa implementuje `CommandLineRunner` — Spring automatycznie wywołuje metodę `run()` zaraz po starcie serwera. Uruchamia zewnętrzny skrypt Python (skompilowany do `.exe`) który generuje realistyczne dane testowe (polskie imiona, nazwiska, numery PESEL) i wysyła je do bazy przez endpointy `/python`.

```java
@Component
public class GeneratorDanychPrzedUruhomieniem implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("./plik_laczacy.exe");
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
    }
}
```

---

## Przykłady requestów

### Dodanie pacjenta
```
POST http://localhost:8080/pacjent/dodaj
Content-Type: application/json

{
    "imie": "Jan",
    "nazwisko": "Kowalski",
    "pesel": "90010112345",
    "dataUrodzenia": "1990-01-01",
    "numerTelefonu": "123456789",
    "adres": "ul. Kwiatowa 1, Warszawa"
}
```

### Zaplanowanie wizyty
```
POST http://localhost:8080/wizyta/dodaj
Content-Type: application/json

{
    "nr_gabinetu": 101,
    "dataWizyty": "2026-05-15",
    "status": "nieodbyty",
    "lekarz": { "id": 1 },
    "pacjent": { "id": 1 }
}
```

### Historia chorób pacjenta
```
GET http://localhost:8080/wizyta/pacjent/1/choroby
```

### Grafik lekarza w zakresie dat
```
GET http://localhost:8080/wizyta/lekarz/1/grafik?od=2025-01-01&do=2025-12-31
```

### Lekarze po specjalizacji
```
GET http://localhost:8080/lekarz/specjalizacja?nazwa=Kardiologia
```

### Sprawdzenie wolnego terminu
```
GET http://localhost:8080/wizyta/wolny-termin?lekarzId=1&data=2026-05-15
```
Zwraca `true` jeśli termin wolny, `false` jeśli zajęty.

### Raport chorób ICD-10 w danym miesiącu
```
GET http://localhost:8080/wizyta/raport/icd10?miesiac=8&rok=2025
```

### Obłożenie gabinetów w danym dniu
```
GET http://localhost:8080/wizyta/gabinety?data=2025-08-19
```
