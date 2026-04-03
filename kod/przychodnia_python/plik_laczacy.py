from automatyczne_uzupelnianie import AkcjaGenerowanie

program = AkcjaGenerowanie()

program.eksportuj(liczba_pacjentow=30, liczba_lekarzy=8, liczba_wizyt=100)

#skrypt tworzacy exe dla mac, dla windows zmienic : na ; - pyinstaller --onefile --clean -p . --add-data "data:data" plik_laczacy.py