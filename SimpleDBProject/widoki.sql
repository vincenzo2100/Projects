USE biblioteka;

CREATE VIEW zapytanie1 AS SELECT * FROM ksiazka;
CREATE VIEW zapytanie2 AS SELECT * FROM ksiazka ORDER BY rok_wydania ASC LIMIT 1;
CREATE VIEW zapytanie3 AS SELECT  id_ksiazki,tytul,ksiazka.id_autora,id_kategorii,rok_wydania,id_wydawnictwa FROM ksiazka INNER JOIN autor ON ksiazka.id_autora = autor.id_autora;
CREATE VIEW zapytanie4 AS SELECT id_ksiazki,tytul,ksiazka.id_autora,id_kategorii,rok_wydania,id_wydawnictwa FROM ksiazka RIGHT OUTER JOIN autor ON ksiazka.id_autora = autor.id_autora;
CREATE VIEW zapytanie5 AS SELECT COUNT(id_wypozyczenia) FROM Wypozyczenie WHERE YEAR(data_wypozyczenia) = 2020; 
CREATE VIEW zapytanie6 AS SELECT count(nazwa) AS 'Ilosc ksiazek danego wydawnictwa', nazwa AS 'Wydawnictwo' FROM wydawnictwo RIGHT JOIN ksiazka ON wydawnictwo.id_wydawnictwa = ksiazka.id_wydawnictwa group by nazwa;
CREATE VIEW zapytanie7 AS SELECT CONCAT(imie , ' ' , nazwisko ) AS 'Imie i nazwisko czytelnika',count(wypozyczenie.id_czytelnika) AS 'Ilosc wypozyczen' FROM czytelnik RIGHT JOIN wypozyczenie ON czytelnik.id_czytelnika = wypozyczenie.id_czytelnika GROUP BY wypozyczenie.id_czytelnika;
CREATE VIEW zapytanie8 AS SELECT nazwa,count(nazwa) AS 'Ilosc ksiazek' FROM kategoria RIGHT JOIN ksiazka ON kategoria.id_kategorii = ksiazka.id_kategorii GROUP BY nazwa;
CREATE VIEW zapytanie9 AS SELECT CONCAT(imie,' ',nazwisko) AS 'Imie i nazwisko autora',count(ksiazka.id_autora) AS 'Ilosc ksiazek' FROM autor RIGHT JOIN ksiazka ON autor.id_autora = ksiazka.id_autora GROUP BY ksiazka.id_autora;
CREATE VIEW zapytanie10 AS SELECT CONCAT(imie,' ',nazwisko) AS 'Imie i nazwisko pracownika',count(id_pracownika) AS 'Ilosc autoryzacji' FROM pracownik RIGHT JOIN wypozyczenie ON pracownik.id_pracownika=wypozyczenie.id_pracownika_wypozyczenie GROUP BY wypozyczenie.id_pracownika_wypozyczenie;