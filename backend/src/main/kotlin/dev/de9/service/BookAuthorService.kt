package dev.de9.service

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity

/**
 * 書籍と著者の関係を扱うサービスインターフェース
 */
interface BookAuthorService {
    /**
     * 書籍に著者を追加する。
     * @param bookId 書籍ID
     * @param authorId 著者ID
     * @return 追加した数
     */
    fun add(bookId: Long, authorId: Long): Int

    /**
     * 書籍の著者リストを取得する。
     * @param bookId 書籍ID
     * @return 著者リスト
     */
    fun findAuthorsByBook(bookId: Long): List<AuthorEntity>

    /**
     * 著者の書籍リストを取得する。
     * @param authorId 著者ID
     * @return 書籍リスト
     */
    fun findBooksByAuthor(authorId: Long): List<BookEntity>

    /**
     * 書籍から著者を除く。
     * @param bookId 書籍ID
     * @param authorId 著者ID
     * @return 削除した数
     */
    fun delete(bookId: Long, authorId: Long): Int

}