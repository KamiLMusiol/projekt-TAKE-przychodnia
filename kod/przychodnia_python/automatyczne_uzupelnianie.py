import requests
from Generator import GeneratorDanych


class AkcjaGenerowanie:



    # konfiguracja patrz na port i adres w springboocie

    def __init__(self):
        self.BASE_URL = "http://localhost:8080"




    def wyslij_dane(self, endpoint, dane):
    
        url = f"{self.BASE_URL}/{endpoint}"
        try:
            # wysyłanie json do bazy tych  danych
            response = requests.post(url, json=dane)
    
            if response.status_code not in [200, 201]:
                print(f"wystapil blad {response.status_code}")
    
        except requests.exceptions.ConnectionError:
            print("Spring boot nie działa")




    #kolejnosc ma znaczenie!
    def eksportuj(self, liczba_pacjentow:int = 10, liczba_lekarzy:int = 5, liczba_wizyt:int = 20):



        gen = GeneratorDanych()
    
    
        specjalizacje = gen.generuj_dane_specjalizacja()
        self.wyslij_dane("specjalizacja/python", specjalizacje)
    
        choroby = gen.generuj_dane_choroby()
        self.wyslij_dane("choroba/python", choroby)
    
        pacjenci = gen.generuj_dane_pacjent(liczba_pacjentow)
        self.wyslij_dane("pacjent/python", pacjenci)
    
        lekarze = gen.generuj_dane_lekarz(liczba_lekarzy)
        self.wyslij_dane("lekarz/python", lekarze)
    
    
        wizyty = gen.generuj_dane_wizyta(liczba_wizyt, max_lekarz_id=liczba_lekarzy, max_pacjent_id=liczba_pacjentow)
        self.wyslij_dane("wizyta/python", wizyty)


