package dev.de9.service.impl

import dev.de9.entity.AuthorEntity
import dev.de9.repository.AuthorRepository
import dev.de9.repository.jdbc.JdbcAuthorRepository
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

/**
 * AuthorServiceImplをテスト
 */
@MicronautTest
class AuthorServiceImplTest(
        private val authorRepository: AuthorRepository,
        private val service: AuthorServiceImpl
) : FreeSpec({
    "author service" - {
        "'add' method can call repository" - {
            val mock = getMock(authorRepository)

            every { mock.add(any()) } returns 1

            val entity = AuthorEntity(1, "name")
            service.add(entity) shouldBe 1
            verify { mock.add(entity) }
        }

        "'findById' method can call repository" - {
            val mock = getMock(authorRepository)

            val entity = AuthorEntity(1, "name")
            every { mock.findById(0) } returns null
            every { mock.findById(1) } returns entity

            service.findById(0) shouldBe null
            service.findById(1) shouldBe entity
            verifyOrder {
                mock.findById(0)
                mock.findById(1)
            }
        }

        "'findAuthors' method switches call by name" - {
            val mock = getMock(authorRepository)

            val entitiesFindAll = listOf(
                    AuthorEntity(1, "name"),
                    AuthorEntity(2, "name")
            )
            val entitiesFindByNameLike = listOf(
                    AuthorEntity(1, "name"),
                    AuthorEntity(2, "name")
            )
            every { mock.findAll() } returns entitiesFindAll
            every { mock.findByNameLike(any()) } returns entitiesFindByNameLike

            forAll(
                    row(null, entitiesFindAll),
                    row("", entitiesFindAll),
                    row("name", entitiesFindByNameLike)

            ) { name, expected ->
                service.findAuthors(name) shouldContainExactly expected
            }
            verifyOrder {
                mock.findAll()
                mock.findAll()
                mock.findByNameLike("%name%")
            }
        }

        "'updateName' method can call repository" - {
            val mock = getMock(authorRepository)

            every { mock.updateName(any(), any()) } returns 1

            service.updateName(1, "name") shouldBe 1
            verify { mock.updateName(1, "name") }
        }

        "'delete' method can call repository" - {
            val mock = getMock(authorRepository)

            every { mock.delete(any()) } returns 1

            service.delete(3) shouldBe 1
            verify { mock.delete(3) }
        }
    }
}) {
    @MockBean(JdbcAuthorRepository::class)
    fun authorRepository(): AuthorRepository = mockk()
}
