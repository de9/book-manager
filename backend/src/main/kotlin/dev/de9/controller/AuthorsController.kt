package dev.de9.controller

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.AuthorsOperation
import dev.de9.service.AuthorService
import dev.de9.service.BookAuthorService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller

/**
 * authorsリソースのコントローラー
 */
@Controller(value = "/authors")
class AuthorsController(
        private val authorService: AuthorService,
        private val bookAuthorService: BookAuthorService
) : AuthorsOperation {

    override fun postNewAuthor(author: AuthorEntity): HttpResponse<*> =
            if (authorService.add(author) > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()

    override fun getAuthors(name: String?): List<AuthorEntity> = authorService.findAuthors(name)

    override fun getAuthorById(id: Long): HttpResponse<AuthorEntity> =
            authorService.findById(id)?.let { HttpResponse.ok(it) } ?: HttpResponse.notFound()

    override fun getBooksByAuthor(id: Long): List<BookEntity> = bookAuthorService.findBooksByAuthor(id)

    override fun putAuthor(id: Long, author: AuthorEntity): HttpResponse<*> =
            if (authorService.update(author) > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()

    override fun deleteAuthor(id: Long): HttpResponse<*> =
            if (authorService.delete(id) > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
}