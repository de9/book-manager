package dev.de9.service.impl

import dev.de9.entity.BookEntity
import dev.de9.repository.BookRepository
import dev.de9.service.BookService
import dev.de9.service.result.UpdateBookResult
import java.time.LocalDate
import javax.inject.Singleton

/**
 * 書籍を扱うサービス実装クラス
 */
@Singleton
class BookServiceImpl(private val repository: BookRepository) : BookService {
    override fun add(book: BookEntity): Long? {
        return repository.add(book)
    }

    override fun findById(id: Long): BookEntity? {
        return repository.findById(id)
    }

    override fun findBooks(title: String?): List<BookEntity> {
        return if (title.isNullOrEmpty()) {
            repository.findAll()
        } else {
            repository.findByTitleLike("%$title%")
        }
    }

    override fun update(book: BookEntity): UpdateBookResult {
        val now = LocalDate.now()
        if (book.dateOfPublication < now) return UpdateBookResult.SpecifyPastDate

        repository.findById(book.id)
                .let { it ?: return UpdateBookResult.NotFound }
                .also { if (it.dateOfPublication <= LocalDate.now()) return UpdateBookResult.AlreadyPublished }

        return if (repository.update(book) > 0) UpdateBookResult.Success else UpdateBookResult.NotFound
    }

    override fun delete(id: Long): Int {
        return repository.delete(id)
    }

}