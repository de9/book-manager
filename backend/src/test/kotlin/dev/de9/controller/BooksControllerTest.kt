package dev.de9.controller

import dev.de9.client.BooksClient
import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.service.BookAuthorService
import dev.de9.service.BookService
import dev.de9.service.impl.BookAuthorServiceImpl
import dev.de9.service.impl.BookServiceImpl
import dev.de9.service.result.UpdateBookResult
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest.MicronautKotestExtension.getMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate

/**
 * BooksControllerをテスト
 */
@MicronautTest
class BooksControllerTest(
        private val client: BooksClient,
        private val bookService: BookService,
        private val bookAuthorService: BookAuthorService
) : FreeSpec({
    "books controller" - {
        "'postNewBook' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(bookService)

            every { mock.add(any()) } returns 1

            val entity = BookEntity(0, "title", LocalDate.of(2010, 1, 1))
            client.postNewBook(entity).status shouldBe HttpStatus.OK
            verify { mock.add(entity) }
        }

        "'postNewBook' entrypoint returns HTTP BAD REQUEST on FAILURE" - {
            val mock = getMock(bookService)

            every { mock.add(any()) } returns 0

            val entity = BookEntity(0, "title", LocalDate.of(2010, 1, 1))
            val response = try {
                client.postNewBook(entity)
            } catch (e: HttpClientResponseException) {
                e.response
            }
            response.status shouldBe HttpStatus.BAD_REQUEST
            verify { mock.add(entity) }
        }

        "'getBooks' entrypoint can call service" - {
            val mock = getMock(bookService)

            val expected = listOf(
                    BookEntity(1, "title", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "title", LocalDate.of(2010, 1, 1))
            )

            every { mock.findBooks(any()) } returns expected

            client.getBooks(null) shouldBe expected
            verify { mock.findBooks(null) }
        }

        "'getBookById' entrypoint returns entity with HTTP OK on SUCCESS" - {
            val mock = getMock(bookService)

            val expected = BookEntity(1, "title", LocalDate.of(2010, 1, 1))

            every { mock.findById(any()) } returns expected

            client.getBookById(1).also {
                it.status shouldBe HttpStatus.OK
                it.body() shouldBe expected
            }
            verify { mock.findById(1) }
        }

        "'getBookById' entrypoint returns entity with HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(bookService)

            every { mock.findById(any()) } returns null

            client.getBookById(2).also {
                it.status shouldBe HttpStatus.NOT_FOUND
                it.body() shouldBe null
            }
            verify { mock.findById(2) }
        }

        "'getAuthorsByBook' entrypoint can call service" - {
            val mock = getMock(bookAuthorService)

            val expected = listOf(
                    AuthorEntity(1, "name"),
                    AuthorEntity(2, "name")
            )

            every { mock.findAuthorsByBook(any()) } returns expected

            client.getAuthorsByBook(1) shouldBe expected
            verify { mock.findAuthorsByBook(1) }
        }

        "'putBook' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(bookService)

            every { mock.update(any()) } returns UpdateBookResult.Success

            client.putBook(1, BookEntity(1, "title", LocalDate.of(2010, 1, 1))).status shouldBe HttpStatus.OK
            verify { mock.update(BookEntity(1, "title", LocalDate.of(2010, 1, 1))) }
        }

        "'putBook' entrypoint returns HTTP NOT FOUND if book not found" - {
            val mock = getMock(bookService)

            every { mock.update(any()) } returns UpdateBookResult.NotFound

            val response = client.putBook(2, BookEntity(2, "title", LocalDate.of(2010, 1, 1)))
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.update(BookEntity(2, "title", LocalDate.of(2010, 1, 1))) }
        }

        "'putBook' entrypoint returns HTTP BAD REQUEST if book is already published" - {
            val mock = getMock(bookService)

            every { mock.update(any()) } returns UpdateBookResult.AlreadyPublished

            val response = try {
                client.putBook(2, BookEntity(2, "title", LocalDate.of(2010, 1, 1)))
            } catch (e: HttpClientResponseException) {
                e.response
            }
            response.status shouldBe HttpStatus.BAD_REQUEST
            response.reason() shouldBe BooksController.ALREADY_PUBLISHED_REASON
            verify { mock.update(BookEntity(2, "title", LocalDate.of(2010, 1, 1))) }
        }

        "'putBook' entrypoint returns HTTP BAD REQUEST if specify past date" - {
            val mock = getMock(bookService)

            every { mock.update(any()) } returns UpdateBookResult.SpecifyPastDate

            val response = try {
                client.putBook(2, BookEntity(2, "title", LocalDate.of(2010, 1, 1)))
            } catch (e: HttpClientResponseException) {
                e.response
            }
            response.status shouldBe HttpStatus.BAD_REQUEST
            response.reason() shouldBe BooksController.SPECIFY_PAST_DATE_REASON
            verify { mock.update(BookEntity(2, "title", LocalDate.of(2010, 1, 1))) }
        }

        "'putBookAuthor' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(bookAuthorService)

            every { mock.add(any(), any()) } returns 1

            client.putBookAuthor(1, 2).status shouldBe HttpStatus.OK
            verify { mock.add(1, 2) }
        }

        "'putBookAuthor' entrypoint returns HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(bookAuthorService)

            every { mock.add(any(), any()) } returns 0

            val response = client.putBookAuthor(3, 4)
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.add(3, 4) }
        }

        "'deleteBook' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(bookService)

            every { mock.delete(any()) } returns 1

            client.deleteBook(1).status shouldBe HttpStatus.OK
            verify { mock.delete(1) }
        }

        "'deleteBook' entrypoint returns HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(bookService)

            every { mock.delete(any()) } returns 0

            val response = client.deleteBook(2)
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.delete(2) }
        }

        "'deleteBookAuthor' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(bookAuthorService)

            every { mock.delete(any(), any()) } returns 1

            client.deleteBookAuthor(1, 2).status shouldBe HttpStatus.OK
            verify { mock.delete(1, 2) }
        }

        "'deleteBookAuthor' entrypoint returns HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(bookAuthorService)

            every { mock.delete(any(), any()) } returns 0

            val response = client.deleteBookAuthor(3, 4)
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.delete(3, 4) }
        }
    }
}) {
    @MockBean(BookServiceImpl::class)
    fun bookService(): BookService = mockk()

    @MockBean(BookAuthorServiceImpl::class)
    fun bookAuthorService(): BookAuthorService = mockk()
}