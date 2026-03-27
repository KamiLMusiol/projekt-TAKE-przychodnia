from faker import Faker
import random
import os

class GeneratorDanych:
    def __init__(self):
        self.fake = Faker('pl_PL')
        self.specjalizacje_lekarskie = self.generuj_dane_specjalizacja_1()
        self.choroby = self.generuj_choroby()


    def generuj_dane_pacjent(self, ilosc: int = 5) -> list:
        pacjenci = []
        for _ in range(ilosc):
            pacjenci.append({
                "imie": self.fake.first_name(),
                "nazwisko": self.fake.last_name(),
                "pesel": self.fake.pesel(),
                "dataUrodzenia": self.fake.date_of_birth(minimum_age=18, maximum_age=90).strftime('%Y-%m-%d'),
                "numerTelefonu": self.fake.phone_number(),
                "adres": self.fake.address().replace('\n', ', ')})
        return pacjenci

    #specjalizacje mają byc stałe
    def generuj_dane_specjalizacja(self):
        return self.specjalizacje_lekarskie

    def generuj_dane_specjalizacja_1(self) -> list:
        specjalizacje = [
            {
                "nazwa_specjalizacji": "Kardiologia",
                "opis": "Diagnostyka i leczenie chorób układu krążenia."
            },
            {
                "nazwa_specjalizacji": "Neurologia",
                "opis": "Diagnostyka i leczenie chorób układu nerwowego."
            },
            {
                "nazwa_specjalizacji": "Pediatria",
                "opis": "Profilaktyka i leczenie chorób dziecięcych."
            },
            {
                "nazwa_specjalizacji": "Ortopedia",
                "opis": "Leczenie urazów i schorzeń narządu ruchu."
            },
            {
                "nazwa_specjalizacji": "Rodzinny",
                "opis": "Wszystko inne."
            },
        ]
        return specjalizacje

    def generuj_dane_lekarz(self, ilosc: int = 5) -> list:
        lekarze = []
        ilosc_sepecek_maks = min(3, len(self.specjalizacje_lekarskie))
        lista_id_specjalizacji = list(range(1, len(self.specjalizacje_lekarskie) + 1))


        for _ in range(ilosc):
            ile_specek = random.randint(1, ilosc_sepecek_maks) #lekarz moze miec od 1 do 3 specek by nie przesadzic chyba ze jest mniej specek
            wylosowane = random.sample(lista_id_specjalizacji, ile_specek) #lostujemy id specjalizacji jakie lekarz będzie posiadał
            lekarze.append({
                "imie": self.fake.first_name(),
                "nazwisko": self.fake.last_name(),
                "numerPwz": str(random.randint(1000000, 9999999)),
                "email": self.fake.email(),
                "specjalizacje": list(map(lambda i: {"id": i}, wylosowane))
            })
        return lekarze

#chroby
    def generuj_choroby(self):
        dir_gen = os.path.dirname(os.path.abspath(__file__)) # aktualna ścieżka
        dir_for_data = os.path.join(dir_gen, 'data','choroby.txt' ) #sciezka do danych zapisanych w txt dla wygody i przejrzystosci
        lista_chorob = []

        try:
            with open(dir_for_data, 'r', encoding='utf-8') as f:
                for line in f:
                    if ',' in line: #bo wcześniej caasem wykrywał pustą linie na końcu
                        nazwa,opis = line.strip().split(',',1)
                        lista_chorob.append({"nazwa": nazwa, "opis": opis})
            return lista_chorob
        except FileNotFoundError:
            print("Błąd: Nie znaleziono pliku/nie ma go")
            return []

    def generuj_dane_choroby(self):
        return self.choroby
    
                                                                





    def generuj_dane_wizyta(self, ilosc: int = 5, max_lekarz_id: int =5, max_pacjent_id: int = 5) -> list:
        wizyty = []
        id_wszystkich_chorob = list(range(1, len(self.choroby) + 1)) # pula id choróbsk

        for _ in range(ilosc):
            ile_chorob = random.randint(1, min(3, len(self.choroby))) # 1 do 3 chorób by nie przesadzic chyba ze mniej chorob
            wylosowane_id_chorob = random.sample(id_wszystkich_chorob, ile_chorob)

            wizyty.append({
                "dataWizyty": self.fake.date_time_between(start_date="-1y", end_date="now").strftime('%Y-%m-%d'), #format który przyjmie JAVA NIE ZMIENIAĆ!!!!!!!
                "zalecenia": "coś tam coś tam daj 100 zł masz lizaka widzimy się za dwa lata",
                "lekarz": {"id": random.randint(1, max_lekarz_id)},
                "pacjent": {"id": random.randint(1, max_pacjent_id)},
                "choroby": list(map(lambda i: {"id": i}, wylosowane_id_chorob))
            })
        return wizyty
