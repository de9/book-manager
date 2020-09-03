package dev.de9.repository.jdbc

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MicronautTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDate

/**
 * JdbcBookRepositoryをテスト
 */
@MicronautTest
class JdbcBookAuthorRepositoryTest(
        private val repository: JdbcBookAuthorRepository,
        private val bookRepository: JdbcBookRepository,
        private val authorRepository: JdbcAuthorRepository,
        private val jdbcTemplate: NamedParameterJdbcTemplate
) : FreeSpec({

    "book author repository" - {
        "can add relation of book and author" - {
            // 書籍と著者が存在する場合のみ追加できる
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            authorRepository.add(AuthorEntity(0, "test name1"))

            val result = repository.add(1, 1)
            result shouldBe 1
        }

        "can NOT add relation if book or author does NOT exist" - {
            // 書籍と著者が存在する場合のみ追加できる
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))

            val result1 = repository.add(1, 1)
            result1 shouldBe 0

            bookRepository.delete(1)
            authorRepository.add(AuthorEntity(0, "test name1"))

            val result2 = repository.add(1, 1)
            result2 shouldBe 0
        }

        "can find books by author" - {
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            bookRepository.add(BookEntity(0, "test title2", LocalDate.of(2010, 1, 1)))
            bookRepository.add(BookEntity(0, "test title3", LocalDate.of(2010, 1, 1)))
            authorRepository.add(AuthorEntity(0, "test name1"))
            authorRepository.add(AuthorEntity(0, "test name2"))
            repository.add(1, 1)
            repository.add(2, 1)

            val results = repository.findBooksByAuthor(1)
            results.shouldContainAll(
                    BookEntity(1, "test title1", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "test title2", LocalDate.of(2010, 1, 1))
            )
        }

        "can find authors by book" - {
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            bookRepository.add(BookEntity(0, "test title2", LocalDate.of(2010, 1, 1)))
            authorRepository.add(AuthorEntity(0, "test name1"))
            authorRepository.add(AuthorEntity(0, "test name2"))
            authorRepository.add(AuthorEntity(0, "test name3"))
            repository.add(1, 1)
            repository.add(1, 2)

            val results = repository.findAuthorsByBook(1)
            results.shouldContainAll(
                    AuthorEntity(1, "test name1"),
                    AuthorEntity(2, "test name2")
            )
        }

        "can remove relation" - {
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            authorRepository.add(AuthorEntity(0, "test name1"))
            repository.add(1, 1)

            val result = repository.delete(1, 1)
            result shouldBe 1
        }

        "can NOT remove relation if it NOT exist" - {
            bookRepository.add(BookEntity(0, "test title1", LocalDate.of(2010, 1, 1)))
            authorRepository.add(AuthorEntity(0, "test name1"))
            repository.add(1, 1)

            val result = repository.delete(2, 1)
            result shouldBe 0
        }
    }
}) {
    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        // テーブルの初期化
        if (!testCase.isTopLevel()) {
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE;", mapOf<String, String>())
            jdbcTemplate.update("TRUNCATE TABLE book;", mapOf<String, String>())
            jdbcTemplate.update("ALTER TABLE book ALTER COLUMN id LONG AUTO_INCREMENT(1);", mapOf<String, String>())
            jdbcTemplate.update("TRUNCATE TABLE author;", mapOf<String, String>())
            jdbcTemplate.update("ALTER TABLE author ALTER COLUMN id LONG AUTO_INCREMENT(1);", mapOf<String, String>())
            jdbcTemplate.update("TRUNCATE TABLE book_author;", mapOf<String, String>())
            jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE;", mapOf<String, String>())
        }
    }
}
