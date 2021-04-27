package com.jetbrains.life_science.category.entity

import com.jetbrains.life_science.article.master.entity.Article
import javax.persistence.*

@Entity
class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @ManyToOne
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent")
    val subCategories: MutableList<Category>,

    @OneToMany(mappedBy = "category")
    val articles: MutableList<Article>,

    val orderNumber: Int
)

abstract class Example() {

    lateinit var kek: String

    fun lol(data: String = kek) {

    }

}
