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

        return id == other.id &&
            name == other.name &&
            hidden == other.hidden &&
            published == other.published &&
            order == other.order
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
