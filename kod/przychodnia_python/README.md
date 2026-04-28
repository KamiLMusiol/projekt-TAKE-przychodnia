# Przychodnia Python — Generator Danych Testowych

Skrypt Python odpowiedzialny za **automatyczne generowanie i zasilanie bazy danych** systemu przychodni. Generuje realistyczne polskie dane testowe i wysyła je do Spring Boot API przez HTTP.

---

## Struktura projektu

```
przychodnia_python/
├── Generator.py                  # Generowanie danych (Faker)
├── automatyczne_uzupelnianie.py  # Wysyłanie danych do API
├── plik_laczacy.py               # Punkt wejścia — uruchamia cały proces
├── data/
│   └── choroby.txt               # Plik z listą chorób (kod ICD-10, nazwa, opis)
└── testowanie/
    ├── testowanie_wyswietlanie_json.py   # Narzędzie do podglądu danych w konsoli
    └── testowanie_wyswietlania_cz2.ipynb # Jupyter Notebook do testów
```

---

## Wymagania

```bash
pip install faker requests
```

---

## Uruchomienie

> ⚠️ Spring Boot musi być uruchomiony przed odpaleniem skryptu (`http://localhost:8080`)

```bash
python plik_laczacy.py
```

Skrypt wygeneruje i wyśle do bazy:
- 5 specjalizacji (stałe)
- 30 chorób (z pliku `choroby.txt`)
- 30 pacjentów
- 8 lekarzy
- 100 wizyt

Liczby można zmienić w `plik_laczacy.py`.

### Budowanie pliku wykonywalnego (.exe / binarki)
```bash
# macOS / Linux
pyinstaller --onefile --clean -p . --add-data "data:data" plik_laczacy.py

# Windows (zmień : na ;)
pyinstaller --onefile --clean -p . --add-data "data;data" plik_laczacy.py
```
Gotowy plik trafia do folderu `dist/` i jest automatycznie wywoływany przez Spring Boot przy starcie.

---

## Opis plików

### `plik_laczacy.py` — punkt wejścia

Prosty skrypt który tworzy obiekt `AkcjaGenerowanie` i wywołuje metodę `eksportuj()` z określonymi parametrami.

```python
program = AkcjaGenerowanie()
program.eksportuj(liczba_pacjentow=30, liczba_lekarzy=8, liczba_wizyt=100)
```

Tu zmieniasz ile danych ma trafić do bazy.

---

### `Generator.py` — generowanie danych

Klasa `GeneratorDanych` odpowiada za tworzenie realistycznych danych testowych przy użyciu biblioteki **Faker** z lokalizacją polską (`pl_PL`).

#### Inicjalizacja
```python
def __init__(self):
    self.fake = Faker('pl_PL')                        # polski Faker
    self.specjalizacje_lekarskie = self.generuj_dane_specjalizacja_1()
    self.choroby = self.generuj_choroby()             # wczytane z pliku txt
```
Specjalizacje i choroby są wczytywane raz przy tworzeniu obiektu — są stałe dla każdego uruchomienia.

#### Generowanie pacjentów
```python
def generuj_dane_pacjent(self, ilosc: int = 5) -> list:
```
Tworzy listę słowników z polskimi imionami, nazwiskami, numerami PESEL, datami urodzenia, telefonami i adresami. Każdy słownik odpowiada strukturze encji `Pacjent` w Spring Boot.

```python
{
    "imie": "Jan",
    "nazwisko": "Kowalski",
    "pesel": "90010112345",
    "dataUrodzenia": "1990-01-01",   # format wymagany przez Javę
    "numerTelefonu": "123 456 789",
    "adres": "ul. Kwiatowa 1, Warszawa"
}
```

#### Generowanie lekarzy
```python
def generuj_dane_lekarz(self, ilosc: int = 5) -> list:
```
Każdy lekarz dostaje losowo od 1 do 3 specjalizacji (losowanie bez powtórzeń z puli dostępnych ID). Specjalizacje są przekazywane jako lista obiektów z samym `id` — Spring Boot sam uzupełni relację.

```python
{
    "imie": "Anna",
    "nazwisko": "Nowak",
    "numerPwz": "1234567",
    "email": "anna.nowak@example.com",
    "specjalizacje": [{"id": 1}, {"id": 3}]  # referencje do istniejących specjalizacji
}
```

#### Generowanie chorób
```python
def generuj_choroby(self):
```
Choroby nie są generowane losowo — są wczytywane z pliku `data/choroby.txt`. Każda linia pliku to:
```
KodICD10,Nazwa,Opis
```
np.:
```
I10,Nadciśnienie tętnicze,Trwałe podwyższenie ciśnienia krwi...
```
Metoda parsuje każdą linię i tworzy słownik. Jeśli plik nie istnieje — zwraca pustą listę z komunikatem błędu.

#### Generowanie wizyt
```python
def generuj_dane_wizyta(self, ilosc, max_lekarz_id, max_pacjent_id) -> list:
```
Każda wizyta dostaje:
- losową datę z ostatniego roku
- losowy numer gabinetu (1-300)
- losowego lekarza i pacjenta (z zakresu już dodanych do bazy)
- od 1 do 3 losowych chorób

```python
{
    "dataWizyty": "2025-08-19",          # format YYYY-MM-DD wymagany przez Javę
    "nr_gabinetu": 101,
    "zalecenia": "...",
    "lekarz":  {"id": 3},                # referencja do istniejącego lekarza
    "pacjent": {"id": 7},                # referencja do istniejącego pacjenta
    "choroby": [{"id": 5}, {"id": 12}]   # referencje do istniejących chorób
}
```

> ⚠️ `max_lekarz_id` i `max_pacjent_id` muszą odpowiadać liczbie już dodanych lekarzy i pacjentów — dlatego kolejność wysyłania danych ma znaczenie.

#### Specjalizacje
```python
def generuj_dane_specjalizacja_1(self) -> list:
```
Stała lista 5 specjalizacji — zawsze te same, nie zależy od Fakera. Są identyczne przy każdym uruchomieniu co zapewnia spójność ID w bazie.

---

### `automatyczne_uzupelnianie.py` — wysyłanie danych do API

Klasa `AkcjaGenerowanie` odpowiada za komunikację z Spring Boot API przez HTTP.

#### Metoda `wyslij_dane`
```python
def wyslij_dane(self, endpoint, dane):
    url = f"{self.BASE_URL}/{endpoint}"
    response = requests.post(url, json=dane)
```
Wysyła listę obiektów jako JSON na podany endpoint. Biblioteka `requests` automatycznie:
- serializuje listę Pythona do JSON
- ustawia nagłówek `Content-Type: application/json`

Spring Boot odbiera to na endpointach `/python` w kontrolerach.

#### Metoda `eksportuj` — kolejność ma znaczenie!
```python
def eksportuj(self, liczba_pacjentow, liczba_lekarzy, liczba_wizyt):
    self.wyslij_dane("specjalizacja/python", specjalizacje)  # 1. najpierw specjalizacje
    self.wyslij_dane("choroba/python", choroby)              # 2. potem choroby
    self.wyslij_dane("pacjent/python", pacjenci)             # 3. pacjenci
    self.wyslij_dane("lekarz/python", lekarze)               # 4. lekarze (potrzebują specjalizacji)
    self.wyslij_dane("wizyta/python", wizyty)                # 5. na końcu wizyty (potrzebują wszystkiego)
```
Kolejność jest kluczowa — wizyta odwołuje się do ID lekarza i pacjenta, więc muszą już istnieć w bazie. Lekarz odwołuje się do ID specjalizacji, więc specjalizacje muszą być pierwsze.

---

### `testowanie/testowanie_wyswietlanie_json.py` — podgląd danych

Pomocnicza funkcja do wyświetlania wygenerowanych danych w konsoli w czytelnym formacie JSON — używana tylko do testów lokalnych, nie wysyła nic do API.

```python
def show_as_json(nazwa, dane):
    print(f"\n{'='*20} {nazwa.upper()} {'='*20}")
    print(json.dumps(dane, indent=4, ensure_ascii=False))
```
`ensure_ascii=False` — bez tego polskie znaki (ą, ę, ó...) byłyby zamieniane na kody Unicode.

---

### `testowanie/testowanie_wyswietlania_cz2.ipynb` — Jupyter Notebook

Notatnik do interaktywnego testowania generatora — pozwala podglądać wygenerowane dane dla każdej encji osobno bez uruchamiania całego procesu eksportu. Przydatny przy rozwijaniu i debugowaniu generatora.

---

## Przepływ danych

```
plik_laczacy.py
       │
       ▼
AkcjaGenerowanie.eksportuj()
       │
       ├── GeneratorDanych.generuj_dane_specjalizacja()  ──► POST /specjalizacja/python
       ├── GeneratorDanych.generuj_dane_choroby()        ──► POST /choroba/python
       ├── GeneratorDanych.generuj_dane_pacjent()        ──► POST /pacjent/python
       ├── GeneratorDanych.generuj_dane_lekarz()         ──► POST /lekarz/python
       └── GeneratorDanych.generuj_dane_wizyta()         ──► POST /wizyta/python
                                                                    │
                                                                    ▼
                                                            Spring Boot API
                                                                    │
                                                                    ▼
                                                            Baza danych H2
```
