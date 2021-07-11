package com.jetbrains.life_science.category.entity

import com.jetbrains.life_science.approach.entity.PublicApproach
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "category_sub_categories",
        joinColumns = [JoinColumn(name = "parents_id")],
        inverseJoinColumns = [JoinColumn(name = "sub_categories_id")]
    )
    val subCategories: MutableList<Category>,

    @ManyToMany
    val approaches: MutableList<PublicApproach>,

    @ManyToMany
    @JoinTable(
        name = "category_sub_categories",
        joinColumns = [JoinColumn(name = "sub_categories_id")],
        inverseJoinColumns = [JoinColumn(name = "parents_id")]
    )
    val parents: MutableList<Category>,

    var creationDate: LocalDateTime
) {

    val isEmpty: Boolean get() = subCategories.isEmpty() && approaches.isEmpty()
}
