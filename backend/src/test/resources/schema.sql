CREATE TABLE book
(
    id                  VARCHAR AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR,
    date_of_publication DATE NOT NULL
);

CREATE TABLE author
(
    id   VARCHAR AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE book_author
(
    book_id   VARCHAR,
    author_id VARCHAR,
    CONSTRAINT fk__book_author__book_id FOREIGN KEY (book_id) REFERENCES book (id),
    CONSTRAINT fk__book_author__author_id FOREIGN KEY (author_id) REFERENCES author (id)
);
