# Projekt główny przychodni - `Java`

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
