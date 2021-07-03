package com.jetbrains.life_science.article.primary.entity

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.category.entity.Category
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "categories_articles",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableList<Category>,

    @OneToMany(mappedBy = "mainArticle")
    val versions: MutableList<ArticleVersion>,

) {

    val hasPublishedVersions: Boolean get() = versions.any { it.state == State.PUBLISHED_AS_ARTICLE }
}
