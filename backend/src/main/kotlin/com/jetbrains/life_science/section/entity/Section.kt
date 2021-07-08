package com.jetbrains.life_science.section.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Section(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @Column(name = "order_num")
    var order: Long,

    var visible: Boolean,

    var published: Boolean

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Section

        if (id != other.id) return false
        if (name != other.name) return false
        if (order != other.order) return false
        if (visible != other.visible) return false
        if (published != other.published) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + order.hashCode()
        result = 31 * result + visible.hashCode()
        result = 31 * result + published.hashCode()
        return result
    }
}
