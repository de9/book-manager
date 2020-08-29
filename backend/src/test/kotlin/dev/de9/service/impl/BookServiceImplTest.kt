package dev.de9.service.impl

import dev.de9.entity.BookEntity
import dev.de9.repository.BookRepository
import dev.de9.repository.jdbc.JdbcBookRepository
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest.MicronautKotestExtension.getMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import java.time.LocalDate

/**
 * BookServiceImplをテスト
 */
@MicronautTest
class BookServiceImplTest(
        private val bookRepository: BookRepository,
        private val service: BookServiceImpl
) : FreeSpec({
    "book service" - {
        "'add' method can call repository" - {
            val mock = getMock(bookRepository)

            every { mock.add(any()) } returns 1

            val entity = BookEntity(1, "title", LocalDate.of(2010, 1, 1))
            service.add(entity) shouldBe 1
            verify { mock.add(entity) }
        }

        "'findById' method can call repository" - {
            val mock = getMock(bookRepository)

            val entity = BookEntity(1, "title", LocalDate.of(2010, 1, 1))
            every { mock.findById(0) } returns null
            every { mock.findById(1) } returns entity

            service.findById(0) shouldBe null
            service.findById(1) shouldBe entity
            verifyOrder {
                mock.findById(0)
                mock.findById(1)
            }
        }

        "'findBooks' method switches call by title" - {
            val mock = getMock(bookRepository)

            val entitiesFindAll = listOf(
                    BookEntity(1, "title", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "title", LocalDate.of(2010, 1, 1))
            )
            val entitiesFindByTitleLike = listOf(
                    BookEntity(1, "title", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "title", LocalDate.of(2010, 1, 1))
            )
            every { mock.findAll() } returns entitiesFindAll
            every { mock.findByTitleLike(any()) } returns entitiesFindByTitleLike

            forAll(
                    row(null, entitiesFindAll),
                    row("", entitiesFindAll),
                    row("title", entitiesFindByTitleLike)

            ) { title, expected ->
                service.findBooks(title) shouldContainExactly expected
            }
            verifyOrder {
                mock.findAll()
                mock.findAll()
                mock.findByTitleLike("%title%")
            }
        }

        "'updateTitle' method can call repository" - {
            val mock = getMock(bookRepository)

            every { mock.updateTitle(any(), any()) } returns 1

            service.updateTitle(1, "title") shouldBe 1
            verify { mock.updateTitle(1, "title") }
        }

        "'updateDateOfPublication' method can call repository" - {
            val mock = getMock(bookRepository)

            every { mock.updateDateOfPublication(any(), any()) } returns 1

            service.updateDateOfPublication(2, LocalDate.of(2010, 1, 1)) shouldBe 1
            verify { mock.updateDateOfPublication(2, LocalDate.of(2010, 1, 1)) }
        }

        "'delete' method can call repository" - {
            val mock = getMock(bookRepository)

            every { mock.delete(any()) } returns 1

            service.delete(3) shouldBe 1
            verify { mock.delete(3) }
        }
    }
}) {
    @MockBean(JdbcBookRepository::class)
    fun bookRepository(): BookRepository = mockk()
}
