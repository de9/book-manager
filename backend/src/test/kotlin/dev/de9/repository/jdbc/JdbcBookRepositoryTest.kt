package dev.de9.repository.jdbc

import dev.de9.entity.BookEntity
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MicronautTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDate

/**
 * JdbcBookRepositoryをテスト
 */
@MicronautTest
class JdbcBookRepositoryTest(
        private val repository: JdbcBookRepository,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : FreeSpec({

    "book repository" - {
        "can find book by ID" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            val findResult = repository.findById(2)

            findResult shouldBe BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
        }

        "can NOT find book by NOT exist ID" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            val findResult = repository.findById(3)

            findResult shouldBe null
        }

        "can find all books" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    BookEntity(1, "test title1", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
            )
        }

        "can find books by title" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            val findResults = repository.findByTitleLike("%title1")

            findResults.shouldContainAll(
                    BookEntity(1, "test title1", LocalDate.of(2010, 1, 1))
            )
        }

        "can NOT find books by NOT exist title" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            val findResults = repository.findByTitleLike("NOT exist")

            findResults.shouldBeEmpty()
        }

        "can update book" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            repository.update(BookEntity(1, "updated title", LocalDate.of(2030, 3, 3)))
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    BookEntity(1, "updated title", LocalDate.of(2030, 3, 3)),
                    BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
            )
        }

        "can NOT update book by NON-existent id" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            repository.update(BookEntity(3, "test title1", LocalDate.of(2010, 1, 1)))
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    BookEntity(1, "test title1", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
            )
        }

        "can delete book" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            repository.delete(1)
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
            )
        }

        "can NOT delete book by NOT exist id" - {
            repository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            repository.add(BookEntity(0, "test title2", LocalDate.of(2020, 2, 2)))
            repository.delete(3)
            val findResults = repository.findAll()

            findResults.shouldContainAll(
                    BookEntity(1, "test title1", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "test title2", LocalDate.of(2020, 2, 2))
            )
        }
    }
}) {
    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        // テーブルの初期化
        if (!testCase.isTopLevel()) {
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE;", mapOf<String, String>())
            jdbcTemplate.update("TRUNCATE TABLE book;", mapOf<String, String>())
            jdbcTemplate.update("ALTER TABLE book ALTER COLUMN id INT AUTO_INCREMENT(1);", mapOf<String, String>())
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE;", mapOf<String, String>())
        }
    }
}
