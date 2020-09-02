package dev.de9.repository

import dev.de9.entity.BookEntity

/**
 * 書籍を扱うリポジトリインターフェース
 */
interface BookRepository {
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
     * 全ての書籍を取得する。
     * @return 書籍リスト
     */
    fun findAll(): List<BookEntity>

    /**
     * 書籍をタイトルでLIKE検索する。
     * @param title タイトル
     * @return 書籍リスト
     */
    fun findByTitleLike(title: String): List<BookEntity>

    /**
     * 指定した書籍を更新する。
     * @param book 書籍
     * @return 更新した数
     */
    fun update(book: BookEntity): Int

    /**
     * 指定した書籍を削除する。
     * @param id 書籍ID
     * @return 削除した数
     */
    fun delete(id: Long): Int
}
