package dev.de9.repository

import dev.de9.entity.AuthorEntity

/**
 * 著者を扱うリポジトリインターフェース
 */
interface AuthorRepository {
    /**
     * 著者を追加する。
     * @param author 著者
     * @return 追加した数
     */
    fun persist(author: AuthorEntity): Int

    /**
     * 著者IDを指定して著者を取得する。
     * @param id 著者ID
     * @return 著者
     */
    fun findById(id: Long): AuthorEntity?

    /**
     * 全ての著者を取得する。
     * @return 著者リスト
     */
    fun findAll(): List<AuthorEntity>

    /**
     * 著者を氏名でLIKE検索する。
     * @param name 氏名
     * @return 著者リスト
     */
    fun findByNameLike(name: String): List<AuthorEntity>

    /**
     * 指定した著者の氏名を更新する。
     * @param id 著者ID
     * @param name 氏名
     * @return 更新した数
     */
    fun updateName(id: Long, name: String): Int

    /**
     * 指定した著者を削除する。
     * @param id 著者ID
     * @return 削除した数
     */
    fun delete(id: Long): Int
}