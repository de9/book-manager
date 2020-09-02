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
    override fun add(author: AuthorEntity): Int {
        return repository.add(author)
    }

    override fun findById(id: Long): AuthorEntity? {
        return repository.findById(id)
    }

    override fun findAuthors(name: String?): List<AuthorEntity> {
        return if (name.isNullOrEmpty()) {
            repository.findAll()
        } else {
            repository.findByNameLike("%$name%")
        }
    }

    override fun update(author: AuthorEntity): Int {
        return repository.update(author)
    }

    override fun delete(id: Long): Int {
        return repository.delete(id)
    }

}