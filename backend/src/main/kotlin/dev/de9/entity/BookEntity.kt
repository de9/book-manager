package dev.de9.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

/**
 * 書籍エンティティ
 */
data class BookEntity(
        /** 書籍ID */
        val id: Long,
        /** タイトル */
        val title: String?,
        /** 出版日 */
        @JsonFormat(pattern = "yyyy-MM-dd")
        val dateOfPublication: LocalDate
)
