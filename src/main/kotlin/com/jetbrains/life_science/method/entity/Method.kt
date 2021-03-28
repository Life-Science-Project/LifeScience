package com.jetbrains.life_science.method.entity

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.section.entity.Section
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.validation.constraints.NotBlank

@Entity
class Method(
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
