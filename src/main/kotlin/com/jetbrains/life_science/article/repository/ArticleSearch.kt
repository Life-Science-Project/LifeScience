package com.jetbrains.life_science.article.repository

import com.jetbrains.life_science.article.entity.Article
import org.hibernate.search.mapper.orm.Search
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class ArticleSearch(@PersistenceContext val entityManager: EntityManager) {

    fun search(text: String): List<Article> {
        val session = Search.session(entityManager)

        val result = session.search(Article::class.java)
            .where { f ->
                f.match()
                    .fields("text", "method.name")
                    .matching(text)
            }
            .fetch(10)
        return result.hits() as List<Article>
    }
}
