package dev.de9.controller

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.BooksOperation
import dev.de9.service.BookAuthorService
import dev.de9.service.BookService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import java.time.LocalDate

/**
 * booksリソースのコントローラー
 */
@Controller(value = "/books")
class BooksController(
        private val bookService: BookService,
        private val bookAuthorService: BookAuthorService
) : BooksOperation {

    override fun postNewBook(book: BookEntity): HttpResponse<*> {
        val addCount = bookService.add(book)
        return if (addCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    override fun getBooks(title: String?): List<BookEntity> {
        return bookService.findBooks(title)
    }

    override fun getBookById(id: Long): HttpResponse<BookEntity> {
        val result = bookService.findById(id)
        return if (result != null) HttpResponse.ok(result) else HttpResponse.notFound()
    }

    override fun getAuthorsByBook(id: Long): List<AuthorEntity> {
        return bookAuthorService.findAuthorsByBook(id)
    }

    @Consumes(MediaType.TEXT_PLAIN)
    override fun putTitle(id: Long, title: String): HttpResponse<*> {
        val updateCount = bookService.updateTitle(id, title)
        return if (updateCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    @Consumes(MediaType.TEXT_PLAIN)
    override fun putDateOfPublication(id: Long, dateOfPublication: LocalDate): HttpResponse<*> {
        val updateCount = bookService.updateDateOfPublication(id, dateOfPublication)
        return if (updateCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    override fun putBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> {
        val addCount = bookAuthorService.add(bookId, authorId)
        return if (addCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    override fun deleteBook(id: Long): HttpResponse<*> {
        val deleteCount = bookService.delete(id)
        return if (deleteCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }

    override fun deleteBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> {
        val deleteCount = bookAuthorService.delete(bookId, authorId)
        return if (deleteCount > 0) HttpResponse.ok<Any>() else HttpResponse.badRequest<Any>()
    }
}