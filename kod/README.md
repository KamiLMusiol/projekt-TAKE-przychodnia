# System Przychodnia - Backend (Spring Boot)

Program odpowiedzialny za zarządzanie danymi pacjentów, lekarzy, wizyt, katalogiem chorób i specjalizacji.

## Technologie
* **Java 25**
* **Spring Boot**
* **Spring Data JPA** (Warstwa dostępu do danych)
* **H2 Database** (Baza danych in-memory do testów)
* **Lombok**

## Architektura Systemu

### Model Danych (Entities)
System opiera się na relacyjnym modelu danych z następującymi powiązaniami:
* **Pacjent**: Dane osobowe i kontaktowe.
* **Lekarz**: Zawiera relację `@ManyToMany` ze specjalizacjami.
* **Wizyta**: Centralny punkt systemu. Łączy `Lekarza` i `Pacjenta` (`@ManyToOne`) oraz posiada listę rozpoznanych `Chorób` (`@ManyToMany`).
* **Specjalizacja** oraz **Choroby**: Słowniki danych z opisami.


### Automatyzacja Testów (`GeneratorDanychPrzedUruhomieniem`)
Klasa implementująca `CommandLineRunner` automatyzuje proces zasilania bazy danych:
* Po starcie serwera uruchamia zewnętrzny plik binarny `plik_laczacy.exe`.
* Przechwytuje wyjście strumienia (IO) generatora, wyświetlając logi Pythona bezpośrednio w konsoli Javy.
* Zapewnia dostępność danych testowych od razu po uruchomieniu aplikacji.

W przypadku gdy nie chcemy tego używać trzeba uzupełnić plik sql i zakomentować/wyrzucić  plik automatyzujący generowanie dancyh

## API Endpoints (pliki z endpointami to odpowiedniki entities z przedrostkiem controller)

### Endpointy zbiorcze 
przesyłania list obiektów w formacie JSON:
* `POST /pacjent/python` - Import listy pacjentów.
* `POST /lekarz/python` - Import listy lekarzy wraz z ID specjalizacji.
* `POST /wizyta/python` - Import listy wizyt (powiązania lekarz-pacjent-choroby).
* `POST /choroba/python` - Import słownika chorób.
* `POST /specjalizacja/python` - Import słownika specjalizacji.

### Standardowe operacje (Przykłady)
* `GET /{controller}/pobierz` - Pobiera wszystkie rekordy.
* `GET /{controller}/pobierz/{id}` - Pobiera konkretny rekord po ID.
* `POST /{controller}/dodaj` - Dodaje pojedynczy rekord.
* `DELETE /{controller}/usun/{id}` - Usuwa rekord o danym ID.


## Konfiguracja i Uruchomienie

### Wymagania wstępne
1. Plik `plik_laczacy.exe` (generator) musi znajdować się w głównym katalogu projektu - jeżeli chcemy korzystać z niego w innym przypadku warto albo plik generujący java albo usunąć albo zakomentować.
2. Port `8080` musi być wolny.

### Uruchomienie
`http://localhost:8080/h2-console`
Można skorzystać z postmana









# Podprojekt -  generowanie danych początkowych z pomocą biblioteki faker - `Python`

---

## Generator Danych - Przychodnia
Skrypt w Pythonie służący do automatycznego wypełniania bazy danych systemu przychodni testowymi rekordami.

---

##  Wymagania
* **Python 3.13+** - (potrzebny do stworzenia nowego skryptu exe w projekcie `Java`, gdy mamy plik exe ale nie mamy pythona program główny zadziała bez pobranego Pythona)
* Biblioteki: `requests`, `faker` - przykładowy skrypt pobierający `pip install requests faker`

---

## Struktura plików
* **plik_laczacy.py** - główny plik startowy.
* **automatyczne_uzupelnianie.py** - logika wysyłania żądań POST do API.
* **Generator.py** - klasa generująca losowe dane (Pacjenci, Lekarze, Wizyty).
* **data/choroby.txt** - źródło danych o chorobach
* **testowanie** - folder z plikami w których możemy podejżeć generowane wyniki.

---

## Budowanie pliku EXE
Aby wygenerować plik wykonywalny dla Javy, trzeba użyć PyInstallera:  
`pyinstaller --onefile --paths . --add-data "data;data" plik_laczacy.py`


Wygenerowany plik znajdziemy w folderze dist projektu. Plik ten musimy przenieść do folderu głownego javy (NIE ZMIENIAJĄĆ NAZWY!!!) aby springboot mógł go znaleźć i wygenerować przykładowe dane na początku programu.
Plik zadziała tylko przy działającym sprigboocie na porcie 8080

---

## Endpointy API
### Skrypt komunikuje się z następującymi adresami:
* `POST /specjalizacja/python`
* `POST /choroba/python`
* `POST /pacjent/python`
* `POST /lekarz/python`
* `POST /wizyta/python`
