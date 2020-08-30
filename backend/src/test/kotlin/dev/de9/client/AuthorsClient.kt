package dev.de9.client

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.operation.AuthorsOperation
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client

/**
 * authorsリソースのクライアント
 */
@Client(value = "/authors")
interface AuthorsClient : AuthorsOperation {
    override fun postNewAuthor(author: AuthorEntity): HttpResponse<*>

    override fun getAuthors(name: String?): List<AuthorEntity>

    override fun getAuthorById(id: Long): HttpResponse<AuthorEntity>

    override fun getBooksByAuthor(id: Long): List<BookEntity>

    @Produces(MediaType.TEXT_PLAIN)
    override fun putName(id: Long, name: String): HttpResponse<*>

    override fun deleteAuthor(id: Long): HttpResponse<*>
}