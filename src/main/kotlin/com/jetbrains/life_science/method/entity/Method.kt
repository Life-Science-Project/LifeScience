package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Method (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @NotBlank
    @Column(nullable = false)
    var name: String,

    @ManyToOne
    var section: Section,

    @OneToOne(cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var article: Article
)