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
    var doctorDegree: DoctorDegree = DoctorDegree.NONE,

    @Enumerated
    var academicDegree: AcademicDegree = AcademicDegree.NONE,

    @ManyToMany
    var organisations: MutableList<Organisation>,

    @ManyToMany
    val positions: MutableList<Position>,

    var orcid: String? = null,

    var researchId: String? = null

)
