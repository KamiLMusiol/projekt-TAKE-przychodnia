
/*
--część dla pacjenta 
INSERT INTO pacjent (id, imie, nazwisko, pesel, data_urodzenia, numer_telefonu, adres) 
VALUES (1, 'Jan', 'Kowalski', '80010112345', '1980-01-01', '111222333', 'Katowice');

--czesc dla lekarza
INSERT INTO lekarz (id, imie, nazwisko, numer_pwz, email) 
VALUES (1, 'Anna', 'Nowak', '7654321', 'anna.nowak@przychodnia.pl');

-- czesc dla specjalizacji
INSERT INTO specjalizacja (id, nazwa_specjalizacji, opis) 
VALUES (1, 'Kardiolog', 'Leczenie chorób serca');

-- czesc dla choroby
INSERT INTO choroby (id, nazwa, opis) 
VALUES (1, 'Nadciśnienie', 'Zbyt wysokie ciśnienie krwi');

-- czesc dla wizyta
INSERT INTO wizyta (id, data_wizyty, zalecenia, lekarz_id, pacjent_id) 
VALUES (1, '2026-03-30', 'Regularny pomiar ciśnienia', 1, 1);

-- czesc dla lekarza_specjalizacja ta psorednia
INSERT INTO lekarz_specjalizacja (lekarz_id, specjalizacja_id) 
VALUES (1, 1);

-- czesc dla wizyta_choroba
INSERT INTO wizyta_choroba (wizyta_id, choroba_id) 
VALUES (1, 1);

-- Aktualizacja liczników ID dla bazy danych
ALTER SEQUENCE pacjent_seq RESTART WITH 2;
ALTER SEQUENCE lekarz_seq RESTART WITH 2;
ALTER SEQUENCE specjalizacja_seq RESTART WITH 2;
ALTER SEQUENCE choroby_seq RESTART WITH 2;
ALTER SEQUENCE wizyta_seq RESTART WITH 2;
*/