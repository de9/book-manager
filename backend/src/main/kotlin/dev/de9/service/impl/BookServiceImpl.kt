package dev.de9.service.impl

import dev.de9.entity.BookEntity
import dev.de9.repository.BookRepository
import dev.de9.service.BookService
import java.time.LocalDate
import javax.inject.Singleton

/**
 * 書籍を扱うサービス実装クラス
 */
@Singleton
class BookServiceImpl(private val repository: BookRepository) : BookService {
    override fun add(book: BookEntity): Int {
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

    override fun update(book: BookEntity): Int {
        val now = LocalDate.now()
        if (book.dateOfPublication < now) return -2

        repository.findById(book.id)
                .let { it ?: return 0 }
                .also { if (it.dateOfPublication <= LocalDate.now()) return -1 }

        return repository.update(book)
    }

    override fun delete(id: Long): Int {
        return repository.delete(id)
    }

}