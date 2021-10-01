package com.jetbrains.life_science.section.entity

import javax.persistence.*

@Entity
class Section(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "section_seq"
    )
    @SequenceGenerator(
        name = "section_seq",
        allocationSize = 1
    )
    val id: Long,

    var name: String,

    var hidden: Boolean,

    var published: Boolean,

    @Column(name = "order_num")
    var order: Int

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Section

        if (id != other.id) return false
        if (name != other.name) return false
        if (hidden != other.hidden) return false
        if (published != other.published) return false
        if (order != other.order) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + hidden.hashCode()
        result = 31 * result + published.hashCode()
        result = 31 * result + order.hashCode()
        return result
    }
}
