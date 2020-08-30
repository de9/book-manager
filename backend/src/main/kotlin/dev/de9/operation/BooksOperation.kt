package dev.de9.operation

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import io.micronaut.core.convert.format.Format
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.time.LocalDate

/**
 * booksリソースのオペレーションインターフェース
 */
interface BooksOperation {
    /**
     * 書籍を追加する。
     * @param book 書籍
     * @return
     *  - HTTP_OK: 追加に成功した。
     *  - HTTP_BAD_REQUEST: 追加に失敗した。
     */
    @Post
    fun postNewBook(@Body book: BookEntity): HttpResponse<*>

    /**
     * 書籍をタイトルで部分一致検索する。
     * タイトルを指定しない場合全て取得する。
     * @param title タイトル
     * @return 書籍リスト
     */
    @Get
    fun getBooks(@QueryValue("title") title: String?): List<BookEntity>

    /**
     * 書籍IDを指定して書籍を取得する。
     * @param id 書籍ID
     * @return 書籍
     *  - HTTP_NOT_FOUND: 指定の書籍が存在しなかった。
     */
    @Get(uri = "/{id}")
    fun getBookById(@PathVariable id: Long): HttpResponse<BookEntity>

    /**
     * 書籍の著者リストを取得する。
     * @param id 書籍ID
     * @return 著者リスト
     */
    @Get(uri = "/{id}/authors")
    fun getAuthorsByBook(@PathVariable id: Long): List<AuthorEntity>

    /**
     * 指定した書籍のタイトルを更新する。
     * リクエストボディはtext/plainとする。
     * @param id 書籍ID
     * @param title タイトル
     * @return 更新した数
     *  - HTTP_NOT_FOUND: 指定の書籍が存在しなかった。
     *  - HTTP_BAD_REQUEST: 更新できない書籍を指定した。
     */
    @Put(uri = "/{id}/title")
    fun putTitle(@PathVariable id: Long, @Body title: String): HttpResponse<*>

    /**
     * 指定した書籍の出版日を更新する。
     * リクエストボディはtext/plainとする。
     * @param id 書籍ID
     * @param dateOfPublication 出版日
     * @return 更新した数
     *  - HTTP_NOT_FOUND: 指定の書籍が存在しなかった。
     *  - HTTP_BAD_REQUEST: 更新できない書籍を指定した。
     */
    @Put(uri = "/{id}/date-of-publication")
    fun putDateOfPublication(
            @PathVariable id: Long,
            @Body @Format("yyyy-MM-dd") dateOfPublication: LocalDate
    ): HttpResponse<*>

    /**
     * 書籍に著者を追加する。
     * @param bookId 書籍ID
     * @param authorId 著者ID
     * @return 追加した数
     *  - HTTP_NOT_FOUND: 指定の書籍または著者が存在しなかった。
     */
    @Put(uri = "/{bookId}/authors/{authorId}")
    fun putBookAuthor(@PathVariable bookId: Long, @PathVariable authorId: Long): HttpResponse<*>

    /**
     * 指定した書籍を削除する。
     * @param id 書籍ID
     * @return 削除した数
     *  - HTTP_NOT_FOUND: 指定の書籍が存在しなかった。
     */
    @Delete(uri = "/{id}")
    fun deleteBook(@PathVariable id: Long): HttpResponse<*>

    /**
     * 書籍から著者を除く。
     * @param bookId 書籍ID
     * @param authorId 著者ID
     * @return 削除した数
     *  - HTTP_NOT_FOUND: 指定の書籍または著者が存在しなかった。
     */
    @Delete(uri = "/{bookId}/authors/{authorId}")
    fun deleteBookAuthor(@PathVariable bookId: Long, @PathVariable authorId: Long): HttpResponse<*>
}