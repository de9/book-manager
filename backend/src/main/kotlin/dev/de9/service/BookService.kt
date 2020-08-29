package dev.de9.service

import dev.de9.entity.BookEntity
import java.time.LocalDate

/**
 * 書籍を扱うサービスインターフェース
 */
interface BookService {
    /**
     * 書籍を追加する。
     * @param book 書籍
     * @return 追加した数
     */
    fun add(book: BookEntity): Int

    /**
     * 書籍IDを指定して書籍を取得する。
     * @param id 書籍ID
     * @return 書籍
     */
    fun findById(id: Long): BookEntity?

    /**
     * 書籍をタイトルで部分一致検索する。
     * タイトルを指定しない場合全て取得する。
     * @return 書籍リスト
     */
    fun findBooks(title: String?): List<BookEntity>

    /**
     * 指定した書籍のタイトルを更新する。
     * @param id 書籍ID
     * @param title タイトル
     * @return 更新した数
     */
    fun updateTitle(id: Long, title: String): Int

    /**
     * 指定した書籍の出版日を更新する。
     * @param id 書籍ID
     * @param dateOfPublication 出版日
     * @return 更新した数
     */
    fun updateDateOfPublication(id: Long, dateOfPublication: LocalDate): Int

    /**
     * 指定した書籍を削除する。
     * @param id 書籍ID
     * @return 削除した数
     */
    fun delete(id: Long): Int
}
