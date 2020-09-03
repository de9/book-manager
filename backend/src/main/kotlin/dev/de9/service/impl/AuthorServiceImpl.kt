package dev.de9.service.impl

import dev.de9.entity.AuthorEntity
import dev.de9.repository.AuthorRepository
import dev.de9.service.AuthorService
import javax.inject.Singleton

/**
 * 著者を扱うサービス実装クラス
 */
@Singleton
class AuthorServiceImpl(private val repository: AuthorRepository) : AuthorService {
    override fun add(author: AuthorEntity): Int = repository.add(author)

    override fun findById(id: Long): AuthorEntity? = repository.findById(id)

    override fun findAuthors(name: String?): List<AuthorEntity> =
            if (name.isNullOrEmpty()) {
                // タイトルを指定しない場合全て取得する
                repository.findAll()
            } else {
                // 部分一致検索
                repository.findByNameLike("%$name%")
            }

    override fun update(author: AuthorEntity): Int = repository.update(author)

    override fun delete(id: Long): Int = repository.delete(id)

}