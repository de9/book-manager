package dev.de9.repository.jdbc

import dev.de9.entity.AuthorEntity
import dev.de9.repository.AuthorRepository
import io.micronaut.context.annotation.Requires
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.inject.Singleton

@Singleton
@Requires(beans = [NamedParameterJdbcTemplate::class])
class JdbcAuthorRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : AuthorRepository {
    private companion object {
        const val ADD_SQL =
                "INSERT INTO author (name) VALUES (:name);"
        const val FIND_BY_ID_SQL =
                "SELECT * FROM author WHERE id = :id;"
        const val FIND_ALL_SQL =
                "SELECT * FROM author;"
        const val FIND_BY_NAME_LIKE_SQL =
                "SELECT * FROM author WHERE name LIKE :name;"
        const val UPDATE_NAME_SQL =
                "UPDATE author SET name = :name WHERE id = :id;"
        const val DELETE_SQL =
                "DELETE FROM author WHERE id = :id;"
    }

    override fun add(author: AuthorEntity): Int {
        val params = mapOf("name" to author.name)

        return jdbcTemplate.update(ADD_SQL, params)
    }

    override fun findById(id: Long): AuthorEntity? {
        val params = mapOf("id" to id)

        return jdbcTemplate.query(FIND_BY_ID_SQL, params) { rs, _ ->
            AuthorEntity(
                    id = rs.getLong("id"),
                    name = rs.getString("name")
            )
        }.singleOrNull()
    }

    override fun findAll(): List<AuthorEntity> {
        return jdbcTemplate.query(FIND_ALL_SQL) { rs, _ ->
            AuthorEntity(
                    id = rs.getLong("id"),
                    name = rs.getString("name")
            )
        }
    }

    override fun findByNameLike(name: String): List<AuthorEntity> {
        val params = mapOf("name" to name)

        return jdbcTemplate.query(FIND_BY_NAME_LIKE_SQL, params) { rs, _ ->
            AuthorEntity(
                    id = rs.getLong("id"),
                    name = rs.getString("name")
            )
        }
    }

    override fun updateName(id: Long, name: String): Int {
        val params = mapOf(
                "id" to id,
                "name" to name
        )

        return jdbcTemplate.update(UPDATE_NAME_SQL, params)
    }

    override fun delete(id: Long): Int {
        val sqlParameterMap = mapOf("id" to id)
        return jdbcTemplate.update(DELETE_SQL, sqlParameterMap)
    }

}
