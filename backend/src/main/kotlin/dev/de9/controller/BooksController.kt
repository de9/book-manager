package dev.de9.controller

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.BooksOperation
import dev.de9.service.BookAuthorService
import dev.de9.service.BookService
import dev.de9.service.result.UpdateBookResult
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller

/**
 * booksリソースのコントローラー
 */
@Controller(value = "/books")
class BooksController(
        private val bookService: BookService,
        private val bookAuthorService: BookAuthorService
) : BooksOperation {

    companion object {
        /** 出版済みの書籍を指定した場合のエラーメッセージ */
        const val ALREADY_PUBLISHED_REASON = "This book was already published."

        /** 出版日に過去の日付を指定した場合のエラーメッセージ */
        const val SPECIFY_PAST_DATE_REASON = "Date of publication is not allowed to be past date."
    }

    override fun postNewBook(book: BookEntity): HttpResponse<Long?> =
            bookService.add(book)?.let { HttpResponse.ok(it) } ?: HttpResponse.badRequest()

    override fun getBooks(title: String?): List<BookEntity> =
            bookService.findBooks(title)

    override fun getBookById(id: Long): HttpResponse<BookEntity> =
            bookService.findById(id)?.let { HttpResponse.ok(it) } ?: HttpResponse.notFound()

    override fun getAuthorsByBook(id: Long): List<AuthorEntity> = bookAuthorService.findAuthorsByBook(id)

    override fun putBook(id: Long, book: BookEntity): HttpResponse<*> =
            when (bookService.update(book)) {
                is UpdateBookResult.Success -> HttpResponse.ok<Any>()
                is UpdateBookResult.AlreadyPublished ->
                    HttpResponse.status<Any>(HttpStatus.BAD_REQUEST, ALREADY_PUBLISHED_REASON)
                is UpdateBookResult.SpecifyPastDate ->
                    HttpResponse.status<Any>(HttpStatus.BAD_REQUEST, SPECIFY_PAST_DATE_REASON)
                is UpdateBookResult.NotFound -> HttpResponse.notFound<Any>()
            }

    override fun putBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> =
            if (bookAuthorService.add(bookId, authorId) > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()

    override fun deleteBook(id: Long): HttpResponse<*> =
            if (bookService.delete(id) > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()

    override fun deleteBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> =
            if (bookAuthorService.delete(bookId, authorId) > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
}