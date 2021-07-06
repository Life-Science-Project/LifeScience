package com.jetbrains.life_science.user.master.entity

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    // Credentials
    id: Long,

    email: String,

    password: String,

    refreshToken: String? = null,

    roles: MutableCollection<Role>,

    // Other data
    val firstName: String,

    val lastName: String,

    @Enumerated
    var doctorDegree: DoctorDegree = DoctorDegree.NONE,

    @Enumerated
    var academicDegree: AcademicDegree = AcademicDegree.NONE,

    @ManyToMany
    var organisations: MutableList<Organisation>,

    var orcid: String? = null,

    var researchId: String? = null

) : UserCredentials(
    id = id,
    email = email,
    password = password,
    refreshToken = refreshToken,
    roles = roles
)
