package dev.de9.client

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.BooksOperation
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.annotation.Client

/**
 * booksリソースのクライアント
 */
@Client(value = "/books")
interface BooksClient : BooksOperation {
    override fun postNewBook(book: BookEntity): HttpResponse<Long?>

    override fun getBooks(title: String?): List<BookEntity>

    override fun getBookById(id: Long): HttpResponse<BookEntity>

    override fun getAuthorsByBook(id: Long): List<AuthorEntity>

    override fun putBook(id: Long, book: BookEntity): HttpResponse<*>

    override fun putBookAuthor(bookId: Long, authorId: Long): HttpResponse<*>

    override fun deleteBook(id: Long): HttpResponse<*>

    override fun deleteBookAuthor(bookId: Long, authorId: Long): HttpResponse<*>
}