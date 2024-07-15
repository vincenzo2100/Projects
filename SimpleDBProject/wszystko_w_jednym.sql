CREATE DATABASE Biblioteka;

USE Biblioteka;


CREATE TABLE Autor(
  id_autora INT PRIMARY KEY AUTO_INCREMENT,
  imie VARCHAR(45),
  nazwisko VARCHAR(45));

CREATE TABLE Czytelnik(
  id_czytelnika INT PRIMARY KEY AUTO_INCREMENT,
  imie VARCHAR(45),
  nazwisko VARCHAR(45));

CREATE TABLE Kategoria(
  id_kategorii INT PRIMARY KEY AUTO_INCREMENT,
  nazwa VARCHAR(45));

CREATE TABLE Wydawnictwo(
  id_wydawnictwa INT PRIMARY KEY AUTO_INCREMENT,
  nazwa VARCHAR(45));

CREATE TABLE Pracownik(
  id_pracownika INT PRIMARY KEY AUTO_INCREMENT,
  imie VARCHAR(45),
  nazwisko VARCHAR(45));

CREATE TABLE Ksiazka(
  id_ksiazki INT PRIMARY KEY auto_increment,
  tytul VARCHAR(45),
  id_autora INT,
  id_kategorii INT,
  rok_wydania YEAR,
  id_wydawnictwa INT,
  FOREIGN KEY(id_autora) REFERENCES Autor(id_autora),
  FOREIGN KEY(id_kategorii) REFERENCES Kategoria(id_kategorii),
  FOREIGN KEY(id_wydawnictwa) REFERENCES Wydawnictwo(id_wydawnictwa));


CREATE TABLE Wypozyczenie(
  id_wypozyczenia INT AUTO_INCREMENT PRIMARY KEY,
  id_czytelnika INT,
  id_ksiazki INT,
  id_autora INT,
  id_pracownika_wypozyczenie INT,
  id_kategorii INT,
  id_wydawnictwa INT,
  data_wypozyczenia DATE,
  data_oddania DATE,
  FOREIGN KEY(id_czytelnika) REFERENCES Czytelnik(id_czytelnika),
  FOREIGN KEY(id_ksiazki) REFERENCES Ksiazka(id_ksiazki),
  FOREIGN KEY(id_autora) REFERENCES Autor(id_autora),
  FOREIGN KEY(id_pracownika_wypozyczenie) REFERENCES Pracownik(id_pracownika),
  FOREIGN KEY(id_kategorii) REFERENCES Kategoria(id_kategorii),
  FOREIGN KEY(id_wydawnictwa) REFERENCES Wydawnictwo(id_wydawnictwa));
  

INSERT INTO autor(imie,nazwisko) VALUES ('Jan','Brzechwa'),
('Adam','Mickiewicz'),
('Stephen','King'),
('Wisława','Szymborska'),
('William','Gibson'),
('Howard','Lovecraft'),
('Franz','Kafka'),
('Fiodor','Dostojewski'),
('William','Shakespeare'),
('Charles','Dickens');


INSERT INTO czytelnik(imie,nazwisko) VALUES
('Ferdynand','Kiepski'),
('Tadeusz','Kościński'),
('Paweł','Grom'),
('Tytus','Bomba'),
('Arnold','Boczek'),
('Bogdan','Boner'),
('Sebastian','Grzeloch'),
('Paweł','Szpatuła'),
('Adam','Małysz'),
('Mariusz','Pudzianowski');

INSERT INTO Kategoria(nazwa) VALUES
('Horror'),('S-F'),('Młodzieżowe'),('Komedia'),('Tragedia'),('Kryminał'),('Klasyka'),('Psychologiczna'),('Poradnik'),('Poezja');

INSERT INTO Wydawnictwo(nazwa) VALUES
('Skrzat'),('Greg'),('Werter'),('MAG'),('Muza'),('Helion'),('Wilga'),('Waneko'),('Pascal'),('Sowa');

INSERT INTO pracownik(imie,nazwisko) VALUES
('Adam','Kowalski'),('Jan','Kostka'),('Jadwiga','Karaś');

INSERT INTO Ksiazka(tytul,id_autora,id_kategorii,rok_wydania,id_wydawnictwa) VALUES
('Mona Liza Turbo',5,2,1988,4),
('Zew Cthulhu',6,1,1928,3),
('Instytut',3,1,2019,3),
('To',3,1,1993,3),
('Lśnienie',3,1,1977,3),
('Proces',7,7,1936,10),
('Zbrodnia i kara',8,7, 1956,7),
('Romeo i Julia',9,7,1997,8),
('Opowieść o dwóch miastach',10,7,1967,9),
('Graf Zero',5,2,1990,4);

INSERT INTO Wypozyczenie(id_czytelnika,id_ksiazki,id_autora,id_pracownika_wypozyczenie,id_kategorii,id_wydawnictwa,data_wypozyczenia,data_oddania) VALUES
(1,1,5,1,2,4,'2020-12-01','2020-12-03'),
(6,2,6,3,1,3,'2020-12-04','2020-12-05'),
(4,4,3,1,1,3,'2020-12-06','2020-12-07'),
(7,10,5,1,2,4,'2020-12-08','2020-12-09'),
(8,7,8,3,7,7,'2020-12-10','2020-12-12'),
(9,8,9,2,7,8,'2020-01-13','2020-01-15'),
(5,5,3,3,1,3,'2020-01-16','2020-12-17'),
(10,1,5,1,2,4,'2020-12-17','2020-12-19'),
(9,2,6,3,1,3,'2020-12-20','2020-12-21'),
(7,4,3,1,1,3,'2020-12-22','2020-12-27');


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

/* Wprowadza aktualną datę do kolumny data_wypozyczenia*/
DELIMITER $$
CREATE TRIGGER data_wyp
BEFORE INSERT ON wypozyczenie 
FOR EACH ROW
BEGIN
SET new.data_wypozyczenia = CURDATE();
END$$

/*Sprawdza czy podano prawidłowe id pracownika*/
DELIMITER $$
CREATE TRIGGER pracownik BEFORE INSERT ON wypozyczenie
FOR EACH ROW
BEGIN
IF(new.id_pracownika_wypozyczenie>3)
THEN
SIGNAL SQLSTATE '45000' SET message_text = 'Niepoprawne id pracownika';
END IF;
END$$

/*Automatyzuje dodawanie danych do tabeli Wypozyczenie*/
DELIMITER $$
CREATE PROCEDURE dodawanie_danych(czytelnik int,ksiazka int,autor int,pracownik int,kategoria int,wydawnictwo int)
BEGIN    
INSERT INTO Wypozyczenie(id_czytelnika,id_ksiazki,id_autora,id_pracownika_wypozyczenie,id_kategorii,id_wydawnictwa) VALUES
(czytelnik,ksiazka,autor,pracownik,kategoria,wydawnictwo);
END$$

/*Funkcja pokazuje ile było wypożyczeń w danym roku*/
SET GLOBAL log_bin_trust_function_creators = 1;

DELIMITER $$
CREATE FUNCTION ilosc_wypozyczen_z_danego_roku(n int)
RETURNS INT
BEGIN
SELECT COUNT(id_wypozyczenia) FROM wypozyczenie WHERE YEAR(data_wypozyczenia) = n INTO n;
RETURN n;
END$$
