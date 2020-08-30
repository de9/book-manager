package dev.de9.operation

import dev.de9.entity.AuthorEntity
import dev.de9.entity.BookEntity
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*

/**
 * authorsリソースのオペレーションインターフェース
 */
interface AuthorsOperation {
    /**
     * 著者を追加する。
     * @param author 著者
     * @return
     *  - HTTP_OK: 追加に成功した。
     *  - HTTP_BAD_REQUEST: 追加に失敗した。
     */
    @Post
    fun postNewAuthor(@Body author: AuthorEntity): HttpResponse<*>

    /**
     * 著者を氏名で部分一致検索する。
     * 氏名を指定しない場合全て取得する。
     * @param name 氏名
     * @return 著者リスト
     */
    @Get
    fun getAuthors(@QueryValue("name") name: String?): List<AuthorEntity>

    /**
     * 著者IDを指定して著者を取得する。
     * @param id 著者ID
     * @return 著者
     *  - HTTP_NOT_FOUND: 指定の著者が存在しなかった。
     */
    @Get(uri = "/{id}")
    fun getAuthorById(@PathVariable id: Long): HttpResponse<AuthorEntity>

    /**
     * 著者に紐づく書籍リストを取得する。
     * @param id 著者ID
     * @return 書籍リスト
     */
    @Get(uri = "/{id}/books")
    fun getBooksByAuthor(@PathVariable id: Long): List<BookEntity>

    /**
     * 指定した著者の氏名を更新する。
     * リクエストボディはtext/plainとする。
     * @param id 著者ID
     * @param name 氏名
     * @return 更新した数
     *  - HTTP_NOT_FOUND: 指定の著者が存在しなかった。
     */
    @Put(uri = "/{id}/name")
    fun putName(@PathVariable id: Long, @Body name: String): HttpResponse<*>

    /**
     * 指定した著者を削除する。
     * @param id 著者ID
     * @return 削除した数
     *  - HTTP_NOT_FOUND: 指定の著者が存在しなかった。
     */
    @Delete(uri = "/{id}")
    fun deleteAuthor(@PathVariable id: Long): HttpResponse<*>
}