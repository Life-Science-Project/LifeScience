package com.jetbrains.life_science.article.section.entity

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class Section(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    var description: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_version_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var articleVersion: ArticleVersion,

    var orderNumber: Int,

    var visible: Boolean
) {

    val articleId: Long get() = articleVersion.mainArticle.id
}
