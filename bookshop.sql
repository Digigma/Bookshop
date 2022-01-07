DROP DATABASE IF EXISTS bookshop;

CREATE DATABASE bookshop;

USE bookshop;

CREATE TABLE book
(
   ISBN VARCHAR(17) NOT NULL PRIMARY KEY,
   title VARCHAR(50) NOT NULL,
   firstAuthor VARCHAR(50) NOT NULL,
   price DECIMAL(10, 2) NOT NULL,
   quantityInStock INT DEFAULT 0
);

INSERT INTO book(ISBN, title, firstAuthor, price, quantityInStock) VALUES("978-3-16-148410-1", "The Glass Bead Game", "Hermann Hesse", 23.50, 10);
INSERT INTO book(ISBN, title, firstAuthor, price, quantityInStock) VALUES("977-5-19-147411-1", "The Island of the Day Before", "Umberto Eco", 19.99, 7);
INSERT INTO book(ISBN, title, firstAuthor, price, quantityInStock) VALUES("977-7-25-167477-3", "Tractatus Logico-Philosophicus", "Ludwig Wittgenstein", 34.75, 5);