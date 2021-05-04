package com.jetbrains.life_science.article.master.entity

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.master.entity.User
import javax.persistence.*

@Entity
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    var category: Category,

    @OneToMany(mappedBy = "mainArticle")
    val versions: MutableList<ArticleVersion>,

    @ManyToMany
    val users: MutableList<User>
) {

    val hasPublishedVersions: Boolean get() = versions.any { it.state == State.PUBLISHED }
}
