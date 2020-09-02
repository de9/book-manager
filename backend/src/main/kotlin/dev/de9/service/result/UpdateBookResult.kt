package dev.de9.service.result

/**
 * BookService.updateの結果クラス
 */
sealed class UpdateBookResult {
    /** 成功 */
    object Success : UpdateBookResult()

    /** 指定した書籍が存在しなかった。 */
    object NotFound : UpdateBookResult()

    /** 出版済みの書籍を指定した。 */
    object AlreadyPublished : UpdateBookResult()

    /** 出版日に過去の日付を指定した。 */
    object SpecifyPastDate : UpdateBookResult()
}
