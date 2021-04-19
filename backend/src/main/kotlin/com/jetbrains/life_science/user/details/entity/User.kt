package com.jetbrains.life_science.user.details.entity

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation
import com.jetbrains.life_science.user.position.entity.Position
import javax.persistence.*

@Entity
@Table(name = "user_details")
class User(

    @Id
    @GeneratedValue
    val id: Long,

    val firstName: String,

    val lastName: String,

    @Enumerated
    val doctorDegree: DoctorDegree = DoctorDegree.NONE,

    @Enumerated
    val academicDegree: AcademicDegree = AcademicDegree.NONE,

    @ManyToMany
    val organisations: MutableList<Organisation>,

    @ManyToMany
    val positions: MutableList<Position>,

    val orcid: String? = null,

    val researchId: String? = null

)
