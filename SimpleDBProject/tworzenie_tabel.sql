CREATE DATABASE biblioteka;
USE biblioteka;

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