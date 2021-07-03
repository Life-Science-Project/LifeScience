package com.jetbrains.life_science.category.entity

import com.jetbrains.life_science.article.primary.entity.Article
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

    @ManyToMany(mappedBy = "categories")
    val articles: MutableList<Article>,

    var orderNumber: Int
)
