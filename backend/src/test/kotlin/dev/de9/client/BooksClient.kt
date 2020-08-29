package dev.de9.client

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.BooksOperation
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import java.time.LocalDate

/**
 * booksリソースのクライアント
 */
@Client(value = "/books")
interface BooksClient : BooksOperation {
    override fun postNewBook(book: BookEntity): HttpResponse<*>

    override fun getBooks(title: String?): List<BookEntity>

    override fun getBookById(id: Long): HttpResponse<BookEntity>

    override fun getAuthorsByBook(id: Long): List<AuthorEntity>

    @Produces(MediaType.TEXT_PLAIN)
    override fun putTitle(id: Long, title: String): HttpResponse<*>

    @Produces(MediaType.TEXT_PLAIN)
    override fun putDateOfPublication(id: Long, dateOfPublication: LocalDate): HttpResponse<*>

    override fun putBookAuthor(bookId: Long, authorId: Long): HttpResponse<*>

    override fun deleteBook(id: Long): HttpResponse<*>

    override fun deleteBookAuthor(bookId: Long, authorId: Long): HttpResponse<*>
}