USE Biblioteka;

SELECT * FROM ksiazka;
SELECT * FROM ksiazka ORDER BY rok_wydania ASC LIMIT 1;
SELECT * FROM ksiazka INNER JOIN autor ON ksiazka.id_autora = autor.id_autora;
SELECT * FROM ksiazka RIGHT OUTER JOIN autor ON ksiazka.id_autora = autor.id_autora;
SELECT COUNT(id_wypozyczenia) AS 'Wypozyczenia w roku 2020' FROM Wypozyczenie WHERE YEAR(data_wypozyczenia) = 2020; 
SELECT count(nazwa) AS 'Ilosc ksiazek danego wydawnictwa', nazwa AS 'Wydawnictwo' FROM wydawnictwo RIGHT JOIN ksiazka ON wydawnictwo.id_wydawnictwa = ksiazka.id_wydawnictwa group by nazwa;
SELECT CONCAT(imie , ' ' , nazwisko ) AS 'Imie i nazwisko czytelnika',count(wypozyczenie.id_czytelnika) AS 'Ilosc wypozyczen' FROM czytelnik RIGHT JOIN wypozyczenie ON czytelnik.id_czytelnika = wypozyczenie.id_czytelnika GROUP BY wypozyczenie.id_czytelnika;
SELECT nazwa,count(nazwa) AS 'Ilosc ksiazek' FROM kategoria RIGHT JOIN ksiazka ON kategoria.id_kategorii = ksiazka.id_kategorii GROUP BY nazwa;
SELECT CONCAT(imie,' ',nazwisko) AS 'Imie i nazwisko autora',count(ksiazka.id_autora) AS 'Ilosc ksiazek' FROM autor RIGHT JOIN ksiazka ON autor.id_autora = ksiazka.id_autora GROUP BY ksiazka.id_autora;
SELECT CONCAT(imie,' ',nazwisko) AS 'Imie i nazwisko pracownika',count(id_pracownika) AS 'Ilosc autoryzacji' FROM pracownik RIGHT JOIN wypozyczenie ON pracownik.id_pracownika=wypozyczenie.id_pracownika_wypozyczenie GROUP BY wypozyczenie.id_pracownika_wypozyczenie;

/*
1. Jakie książki istnieją w bazie?
2. Jaka jest najstarsza wydana książka w bazie?
3.Imiona i nazwiska autorów i ich książki
4.Książki jakich autorów znajdują się w bazie?
5.Ilość wypożyczeń w 2020 roku?
6.Z którego wydawnictwa jest największej książek w bazie?
7. Ilość wypożyczeń danego czytelnika
8.Ilość książek danej kategorii
9.Ilość książek danego autora
10.Ilość autoryzacji wypożyczenia danego pracownika
*/