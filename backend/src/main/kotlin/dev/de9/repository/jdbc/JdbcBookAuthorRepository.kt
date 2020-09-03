package dev.de9.repository.jdbc

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.repository.BookAuthorRepository
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.inject.Singleton

/**
 * 書籍と著者の関係を扱うリポジトリのH2-JDBC実装
 */
@Singleton
class JdbcBookAuthorRepository(
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : BookAuthorRepository {
    private companion object {
        const val ADD_SQL =
                "INSERT INTO book_author (book_id, author_id) VALUES (:book_id, :author_id);"
        const val FIND_AUTHORS_BY_BOOK_SQL =
                "SELECT * FROM book_author INNER JOIN author a on a.id = book_author.author_id WHERE book_id = :book_id"
        const val FIND_BOOK_BY_AUTHORS_SQL =
                "SELECT * FROM book_author INNER JOIN book b on b.id = book_author.book_id WHERE author_id = :author_id"
        const val DELETE_SQL =
                "DELETE FROM book_author WHERE book_id = :book_id AND author_id = :author_id;"
    }

    override fun add(bookId: Long, authorId: Long): Int {
        val params = mapOf(
                "book_id" to bookId,
                "author_id" to authorId
        )

        return try {
            jdbcTemplate.update(ADD_SQL, params)
        } catch (e: DataAccessException) {
            // FKey違反などで追加に失敗した場合
            0
        }
    }

    override fun findAuthorsByBook(bookId: Long): List<AuthorEntity> {
        val params = mapOf("book_id" to bookId)

        return jdbcTemplate.query(FIND_AUTHORS_BY_BOOK_SQL, params) { rs, _ ->
            AuthorEntity(
                    id = rs.getLong("id"),
                    name = rs.getString("name")
            )
        }
    }

    override fun findBooksByAuthor(authorId: Long): List<BookEntity> {
        val params = mapOf("author_id" to authorId)

        return jdbcTemplate.query(FIND_BOOK_BY_AUTHORS_SQL, params) { rs, _ ->
            BookEntity(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    dateOfPublication = rs.getDate("date_of_publication").toLocalDate()
            )
        }
    }

    override fun delete(bookId: Long, authorId: Long): Int {
        val params = mapOf(
                "book_id" to bookId,
                "author_id" to authorId
        )

        return jdbcTemplate.update(DELETE_SQL, params)
    }
}