package dev.de9.repository.jdbc

import dev.de9.entity.AuthorEntity
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MicronautTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

/**
 * JdbcAuthorRepositoryをテスト
 */
@MicronautTest
class JdbcAuthorRepositoryTest(
        private val repository: JdbcAuthorRepository,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : FreeSpec({

    "author repository" - {
        "can find author by ID" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            val findResult = repository.findById(2)

            findResult shouldBe AuthorEntity(2, "test name2")
        }

        "can NOT find author by NOT exist ID" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            val findResult = repository.findById(3)

            findResult shouldBe null
        }

        "can find all authors" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    AuthorEntity(1, "test name1"),
                    AuthorEntity(2, "test name2")
            )
        }

        "can find authors by name" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            val findResults = repository.findByNameLike("%name1")

            findResults.shouldContainAll(
                    AuthorEntity(1, "test name1")
            )
        }

        "can NOT find authors by NOT exist name" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            val findResults = repository.findByNameLike("NOT exist")

            findResults.shouldBeEmpty()
        }

        "can update name" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            repository.updateName(1, "updated name")
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    AuthorEntity(1, "updated name"),
                    AuthorEntity(2, "test name2")
            )
        }

        "can NOT update name by NOT exist id" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            repository.updateName(3, "updated name")
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    AuthorEntity(1, "test name1"),
                    AuthorEntity(2, "test name2")
            )
        }

        "can delete author" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            repository.delete(1)
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    AuthorEntity(2, "test name2")
            )
        }

        "can NOT delete author by NOT exist id" - {
            repository.add(AuthorEntity(0, "test name1"))
            repository.add(AuthorEntity(0, "test name2"))
            repository.delete(3)
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    AuthorEntity(1, "test name1"),
                    AuthorEntity(2, "test name2")
            )
        }
    }
}) {
    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        // テーブルの初期化
        if (!testCase.isTopLevel()) {
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE;", mapOf<String, String>())
            jdbcTemplate.update("TRUNCATE TABLE author;", mapOf<String, String>())
            jdbcTemplate.update("ALTER TABLE author ALTER COLUMN id INT AUTO_INCREMENT(1);", mapOf<String, String>())
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE;", mapOf<String, String>())
        }
    }
}
