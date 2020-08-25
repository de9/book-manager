package dev.de9.factory

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.inject.Singleton
import javax.sql.DataSource

@Factory
class JdbcTemplateFactory(private val dataSource: DataSource) {
    @Bean
    @Singleton
    fun jdbcTemplate(): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }
}
