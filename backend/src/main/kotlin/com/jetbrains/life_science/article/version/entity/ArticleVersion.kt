package com.jetbrains.life_science.article.version.entity

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.entity.UserCredentials
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
    var sections: MutableList<Section> = mutableListOf()

) {

    val articleId: Long get() = mainArticle.id

    val isPublished: Boolean get() = state == State.PUBLISHED || state == State.USER_PUBLISHED

    val isArchived: Boolean get() = state == State.PUBLISHED || state == State.USER_PUBLISHED

    fun canRead(user: UserCredentials): Boolean {
        return state == State.PUBLISHED || canModify(user)
    }

    fun canModify(user: UserCredentials): Boolean {
        return author.id == user.id ||
            user.roles.any {
                it.name == "ROLE_ADMIN" ||
                    it.name == "ROLE_MODERATOR"
            }
    }
}
