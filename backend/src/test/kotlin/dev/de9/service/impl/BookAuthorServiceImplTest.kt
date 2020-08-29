package dev.de9.service.impl

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.repository.BookAuthorRepository
import dev.de9.repository.jdbc.JdbcBookAuthorRepository
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest.MicronautKotestExtension.getMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate

/**
 * BookServiceImplをテスト
 */
@MicronautTest
class BookAuthorServiceImplTest(
        private val bookAuthorRepository: BookAuthorRepository,
        private val service: BookAuthorServiceImpl
) : FreeSpec({
    "book service" - {
        "'add' method can call repository" - {
            val mock = getMock(bookAuthorRepository)

            every { mock.add(any(), any()) } returns 1

            service.add(1, 2) shouldBe 1
            verify { mock.add(1, 2) }
        }

        "'findAuthorsByBook' method can call repository" - {
            val mock = getMock(bookAuthorRepository)

            val expected = listOf(
                    AuthorEntity(1, "name"),
                    AuthorEntity(2, "name")
            )
            every { mock.findAuthorsByBook(any()) } returns expected

            service.findAuthorsByBook(1) shouldContainExactly expected
            verify { mock.findAuthorsByBook(1) }
        }

        "'findBooksByAuthor' method can call repository" - {
            val mock = getMock(bookAuthorRepository)

            val expected = listOf(
                    BookEntity(1, "title", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "title", LocalDate.of(2020, 2, 2))
            )
            every { mock.findBooksByAuthor(any()) } returns expected

            service.findBooksByAuthor(1) shouldContainExactly expected
            verify { mock.findBooksByAuthor(1) }
        }

        "'delete' method can call repository" - {
            val mock = getMock(bookAuthorRepository)

            every { mock.delete(any(), any()) } returns 1

            service.delete(3, 4) shouldBe 1
            verify { mock.delete(3, 4) }
        }
    }
}) {
    @MockBean(JdbcBookAuthorRepository::class)
    fun bookAuthorRepository(): BookAuthorRepository = mockk()
}
