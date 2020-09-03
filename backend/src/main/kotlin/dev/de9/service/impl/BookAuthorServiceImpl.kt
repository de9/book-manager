package dev.de9.service.impl

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.repository.BookAuthorRepository
import dev.de9.service.BookAuthorService
import javax.inject.Singleton

/**
 * 書籍を扱うサービス実装クラス
 */
@Singleton
class BookAuthorServiceImpl(private val repository: BookAuthorRepository) : BookAuthorService {
    override fun add(bookId: Long, authorId: Long): Int = repository.add(bookId, authorId)

    override fun findAuthorsByBook(bookId: Long): List<AuthorEntity> = repository.findAuthorsByBook(bookId)

    override fun findBooksByAuthor(authorId: Long): List<BookEntity> = repository.findBooksByAuthor(authorId)

    override fun delete(bookId: Long, authorId: Long): Int = repository.delete(bookId, authorId)

}