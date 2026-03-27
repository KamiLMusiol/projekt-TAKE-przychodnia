# projekt-TAKE-przychodnia
---

## Specyfikacje
* **Języki** - `Java`
* **Technologie** `Spring Tool Suite 5, Lombok`
* **Diagramy** `Klas - draw.io` 

---

## Oprocentowanie 
wprowadzenie do TAKE, strona wyliczająca oprocentowanie

---

##  Architektura
### Oprocentowanie
* **Interfejs** (`Interfejs_Oprocentowanie`) – definicja metody obliczeniowej.
* **Implementacja** (`Oprocentowanie`) – logika matematyczna.
* **Kontroler** (`Controller_Class`) – punkt styku z użytkownikiem (REST API).
* **Main** (`Oprocentowanie1Application`)


---





# Dodatkowe podprojekty


---


# Podprojekt generujący przykładowe losowe dane  


Projekt w Pythonie pełni funkcję automatycznego generatora danych testowych dla bazy danych przychodni. 
Tworzy realistyczne, losowe rekordy pacjentów, lekarzy (z przypisanymi specjalizacjami) oraz wizyt lekarskich przy użyciu biblioteki `Faker`.
Przesyła wygenerowane obiekty w formacie JSON do backendu Spring Boot za pomocą żądań HTTP POST na odpowiednie endpointy.

Przykładowy output dla lekarza:

```json
    {
        "imie": "Janina",
        "nazwisko": "Kozień",
        "numerPwz": "5566431",
        "email": "aaugustynek@example.com",
        "specjalizacje": [
            {
               "id": 2
            },
            {
                "id": 5
            },
            {
                "id": 4
            }
        ]
    },
```


---

Obszerniejsze opisy działania znajdziemy w odpowiednich folderach

