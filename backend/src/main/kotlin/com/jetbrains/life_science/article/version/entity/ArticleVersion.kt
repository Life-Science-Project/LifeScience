package com.jetbrains.life_science.article.version.entity

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.user.entity.User
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

)
