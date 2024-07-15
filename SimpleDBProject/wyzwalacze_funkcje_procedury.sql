USE biblioteka;
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
