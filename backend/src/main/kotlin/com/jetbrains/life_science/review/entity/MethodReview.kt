package com.jetbrains.life_science.review.entity

import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.version.entity.MethodVersion
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class MethodReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    var methodVersion: MethodVersion,

    @Column(nullable = false)
    var comment: String,

    @ManyToOne
    var author: User,
)
