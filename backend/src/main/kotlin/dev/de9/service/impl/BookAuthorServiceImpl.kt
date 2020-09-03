package dev.de9.service.impl

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import dev.de9.repository.BookAuthorRepository
import dev.de9.service.BookAuthorService
import org.springframework.transaction.annotation.Transactional
import javax.inject.Singleton

/**
 * 書籍を扱うサービス実装クラス
 */
@Singleton
open class BookAuthorServiceImpl(private val repository: BookAuthorRepository) : BookAuthorService {
    @Transactional
    override fun add(bookId: Long, authorId: Long): Int = repository.add(bookId, authorId)

    @Transactional(readOnly = true)
    override fun findAuthorsByBook(bookId: Long): List<AuthorEntity> = repository.findAuthorsByBook(bookId)

    @Transactional(readOnly = true)
    override fun findBooksByAuthor(authorId: Long): List<BookEntity> = repository.findBooksByAuthor(authorId)

    @Transactional
    override fun delete(bookId: Long, authorId: Long): Int = repository.delete(bookId, authorId)

}