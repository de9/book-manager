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
    override fun add(book: BookEntity): Long? = repository.add(book)

    override fun findById(id: Long): BookEntity? = repository.findById(id)

    override fun findBooks(title: String?): List<BookEntity> =
            if (title.isNullOrEmpty()) {
                // タイトルを指定しない場合全て取得する
                repository.findAll()
            } else {
                // 部分一致検索
                repository.findByTitleLike("%$title%")
            }

    override fun update(book: BookEntity): UpdateBookResult {
        val now = LocalDate.now()

        repository.findById(book.id)
                // 対象の書籍が存在しない
                .let { it ?: return UpdateBookResult.NotFound }
                // 出版済みの書籍は更新できない
                .also { if (it.dateOfPublication <= now) return UpdateBookResult.AlreadyPublished }

        // 出版日を過去の日付には変更できない
        if (book.dateOfPublication < now) return UpdateBookResult.SpecifyPastDate

        return if (repository.update(book) > 0) UpdateBookResult.Success else UpdateBookResult.NotFound
    }

    override fun delete(id: Long): Int = repository.delete(id)

}