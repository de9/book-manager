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

    override fun postNewAuthor(author: AuthorEntity): HttpResponse<*> {
        val addCount = authorService.add(author)
        return if (addCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    override fun getAuthors(name: String?): List<AuthorEntity> {
        return authorService.findAuthors(name)
    }

    override fun getAuthorById(id: Long): HttpResponse<AuthorEntity> {
        val result = authorService.findById(id)
        return if (result != null) HttpResponse.ok(result) else HttpResponse.notFound()
    }

    override fun getBooksByAuthor(id: Long): List<BookEntity> {
        return bookAuthorService.findBooksByAuthor(id)
    }

    override fun putAuthor(id: Long, author: AuthorEntity): HttpResponse<*> {
        val updateCount = authorService.update(author)
        return if (updateCount > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
    }

    override fun deleteAuthor(id: Long): HttpResponse<*> {
        val deleteCount = authorService.delete(id)
        return if (deleteCount > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
    }
}