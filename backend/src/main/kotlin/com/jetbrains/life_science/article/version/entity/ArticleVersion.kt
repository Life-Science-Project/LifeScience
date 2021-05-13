package com.jetbrains.life_science.article.version.entity

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.parameter.entity.Parameter
import com.jetbrains.life_science.user.master.entity.User
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "state_idx", columnList = "state")])
class ArticleVersion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    var state: State,

    @ManyToOne
    var mainArticle: Article,

    @ManyToOne
    var author: User,

    @OneToMany(mappedBy = "articleVersion", cascade = [CascadeType.ALL])
    var sections: MutableList<Section> = mutableListOf(),

    @OneToMany
    var parameters: MutableList<Parameter> = mutableListOf()

) {

    val articleId: Long get() = mainArticle.id

    val isPublished: Boolean get() = state == State.PUBLISHED_AS_ARTICLE || state == State.PUBLISHED_AS_PROTOCOL

    fun canRead(user: User): Boolean {
        return state == State.PUBLISHED_AS_ARTICLE || canModify(user)
    }

    fun canModify(user: User): Boolean {
        return author.id == user.id ||
            user.roles.any {
                it.name == "ROLE_ADMIN" ||
                    it.name == "ROLE_MODERATOR"
            }
    }
}
