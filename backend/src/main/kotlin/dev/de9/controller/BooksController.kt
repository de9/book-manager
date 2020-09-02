package dev.de9.controller

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.BooksOperation
import dev.de9.service.BookAuthorService
import dev.de9.service.BookService
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

    override fun putBook(id: Long, book: BookEntity): HttpResponse<*> {
        return when (bookService.update(book)) {
            1 -> HttpResponse.ok<Any>()
            -1 -> HttpResponse.status<Any>(HttpStatus.BAD_REQUEST, ALREADY_PUBLISHED_REASON)
            -2 -> HttpResponse.status<Any>(HttpStatus.BAD_REQUEST, SPECIFY_PAST_DATE_REASON)
            else -> HttpResponse.notFound<Any>()
        }
    }

    override fun putBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> {
        val addCount = bookAuthorService.add(bookId, authorId)
        return if (addCount > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
    }

    override fun deleteBook(id: Long): HttpResponse<*> {
        val deleteCount = bookService.delete(id)
        return if (deleteCount > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
    }

    override fun deleteBookAuthor(bookId: Long, authorId: Long): HttpResponse<*> {
        val deleteCount = bookAuthorService.delete(bookId, authorId)
        return if (deleteCount > 0) HttpResponse.ok<Any>() else HttpResponse.notFound<Any>()
    }
}