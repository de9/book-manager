CREATE TABLE IF NOT EXISTS book
(
    id                  VARCHAR AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR,
    date_of_publication DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS author
(
    id   VARCHAR AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS book_author
(
    book_id   VARCHAR,
    author_id VARCHAR,
    CONSTRAINT fk__book_author__book_id FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
    CONSTRAINT fk__book_author__author_id FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);
