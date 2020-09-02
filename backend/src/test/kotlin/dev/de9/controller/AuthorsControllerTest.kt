package dev.de9.controller

import dev.de9.client.AuthorsClient
import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.service.AuthorService
import dev.de9.service.BookAuthorService
import dev.de9.service.impl.AuthorServiceImpl
import dev.de9.service.impl.BookAuthorServiceImpl
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
 * AuthorsControllerをテスト
 */
@MicronautTest
class AuthorsControllerTest(
        private val client: AuthorsClient,
        private val authorService: AuthorService,
        private val authorAuthorService: BookAuthorService
) : FreeSpec({
    "authors controller" - {
        "'postNewAuthor' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(authorService)

            every { mock.add(any()) } returns 1

            val entity = AuthorEntity(0, "name")
            client.postNewAuthor(entity).status shouldBe HttpStatus.OK
            verify { mock.add(entity) }
        }

        "'postNewAuthor' entrypoint returns HTTP BAD REQUEST on FAILURE" - {
            val mock = getMock(authorService)

            every { mock.add(any()) } returns 0

            val entity = AuthorEntity(0, "name")
            val response = try {
                client.postNewAuthor(entity)
            } catch (e: HttpClientResponseException) {
                e.response
            }
            response.status shouldBe HttpStatus.BAD_REQUEST
            verify { mock.add(entity) }
        }

        "'getAuthors' entrypoint can call service" - {
            val mock = getMock(authorService)

            val expected = listOf(
                    AuthorEntity(1, "name"),
                    AuthorEntity(2, "name")
            )

            every { mock.findAuthors(any()) } returns expected

            client.getAuthors(null) shouldBe expected
            verify { mock.findAuthors(null) }
        }

        "'getAuthorById' entrypoint returns entity with HTTP OK on SUCCESS" - {
            val mock = getMock(authorService)

            val expected = AuthorEntity(1, "name")

            every { mock.findById(any()) } returns expected

            client.getAuthorById(1).also {
                it.status shouldBe HttpStatus.OK
                it.body() shouldBe expected
            }
            verify { mock.findById(1) }
        }

        "'getAuthorById' entrypoint returns entity with HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(authorService)

            every { mock.findById(any()) } returns null

            client.getAuthorById(2).also {
                it.status shouldBe HttpStatus.NOT_FOUND
                it.body() shouldBe null
            }
            verify { mock.findById(2) }
        }

        "'getAuthorsByAuthor' entrypoint can call service" - {
            val mock = getMock(authorAuthorService)

            val expected = listOf(
                    BookEntity(1, "name", LocalDate.of(2010, 1, 1)),
                    BookEntity(2, "name", LocalDate.of(2010, 1, 1))
            )

            every { mock.findBooksByAuthor(any()) } returns expected

            client.getBooksByAuthor(1) shouldBe expected
            verify { mock.findBooksByAuthor(1) }
        }

        "'putAuthor' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(authorService)

            every { mock.update(any()) } returns 1

            client.putAuthor(1, AuthorEntity(1, "name")).status shouldBe HttpStatus.OK
            verify { mock.update(AuthorEntity(1, "name")) }
        }

        "'putAuthor' entrypoint returns HTTP NOT FOUND if author not found" - {
            val mock = getMock(authorService)

            every { mock.update(any()) } returns 0

            val response = client.putAuthor(2, AuthorEntity(2, "name"))
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.update(AuthorEntity(2, "name")) }
        }

        "'deleteAuthor' entrypoint returns HTTP OK on SUCCESS" - {
            val mock = getMock(authorService)

            every { mock.delete(any()) } returns 1

            client.deleteAuthor(1).status shouldBe HttpStatus.OK
            verify { mock.delete(1) }
        }

        "'deleteAuthor' entrypoint returns HTTP NOT FOUND on FAILURE" - {
            val mock = getMock(authorService)

            every { mock.delete(any()) } returns 0

            val response = client.deleteAuthor(2)
            response.status shouldBe HttpStatus.NOT_FOUND
            verify { mock.delete(2) }
        }
    }
}) {
    @MockBean(AuthorServiceImpl::class)
    fun authorService(): AuthorService = mockk()

    @MockBean(BookAuthorServiceImpl::class)
    fun bookAuthorService(): BookAuthorService = mockk()
}