DELETE FROM book_categories;
DELETE FROM categories;
DELETE FROM books;

INSERT INTO books (id, title, author, isbn, price)
VALUES (1 ,'Kobzar', 'Taras Shevchenko', '555555_5', 119.99);

INSERT INTO books (id, title, author, isbn, price)
VALUES (2, 'Thinking Java', 'Bruce Eckel', '6666666_6', 99.99);

INSERT INTO categories (id, name, description) VALUES (1, 'TestCategory1', 'Test description');
INSERT INTO categories (id, name) VALUES (2, 'TestCategory2');
INSERT INTO categories (id, name) VALUES (3, 'TestCategory3');

INSERT INTO book_categories (book_id, category_id) VALUES (1, 1);
INSERT INTO book_categories (book_id, category_id) VALUES (1, 2);
INSERT INTO book_categories (book_id, category_id) VALUES (2, 2);