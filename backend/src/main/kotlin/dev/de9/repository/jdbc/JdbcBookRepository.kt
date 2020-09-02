package dev.de9.repository.jdbc

import dev.de9.entity.BookEntity
import dev.de9.repository.BookRepository
import io.micronaut.context.annotation.Requires
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.inject.Singleton

/**
 * 書籍を扱うリポジトリのH2-JDBC実装
 */
@Singleton
@Requires(beans = [NamedParameterJdbcTemplate::class])
class JdbcBookRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : BookRepository {
    private companion object {
        const val ADD_SQL =
                "INSERT INTO book (title, date_of_publication) VALUES (:title, :date_of_publication);"
        const val FIND_BY_ID_SQL =
                "SELECT * FROM book WHERE id = :id;"
        const val FIND_ALL_SQL =
                "SELECT * FROM book;"
        const val FIND_BY_TITLE_LIKE_SQL =
                "SELECT * FROM book WHERE title LIKE :title;"
        const val UPDATE_SQL =
                "UPDATE book SET title = :title, date_of_publication = :date_of_publication WHERE id = :id;"
        const val DELETE_SQL =
                "DELETE FROM book WHERE id = :id;"
    }

    override fun add(book: BookEntity): Int {
        val params = mapOf(
                "title" to book.title,
                "date_of_publication" to book.dateOfPublication
        )

        return jdbcTemplate.update(ADD_SQL, params)
    }

    override fun findById(id: Long): BookEntity? {
        val params = mapOf("id" to id)

        return jdbcTemplate.query(FIND_BY_ID_SQL, params) { rs, _ ->
            BookEntity(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    dateOfPublication = rs.getDate("date_of_publication").toLocalDate()
            )
        }.singleOrNull()
    }

    override fun findAll(): List<BookEntity> {
        return jdbcTemplate.query(FIND_ALL_SQL) { rs, _ ->
            BookEntity(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    dateOfPublication = rs.getDate("date_of_publication").toLocalDate()
            )
        }
    }

    override fun findByTitleLike(title: String): List<BookEntity> {
        val params = mapOf("title" to title)

        return jdbcTemplate.query(FIND_BY_TITLE_LIKE_SQL, params) { rs, _ ->
            BookEntity(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    dateOfPublication = rs.getDate("date_of_publication").toLocalDate()
            )
        }
    }

    override fun update(book: BookEntity): Int {
        val params = mapOf(
                "id" to book.id,
                "title" to book.title,
                "date_of_publication" to book.dateOfPublication
        )

        return jdbcTemplate.update(UPDATE_SQL, params)
    }

    override fun delete(id: Long): Int {
        val sqlParameterMap = mapOf("id" to id)
        return jdbcTemplate.update(DELETE_SQL, sqlParameterMap)
    }
}