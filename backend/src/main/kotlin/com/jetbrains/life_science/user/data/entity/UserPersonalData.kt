package com.jetbrains.life_science.user.data.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation
import javax.persistence.*

@Entity
@Table(name = "users")
class UserPersonalData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val firstName: String,

    val lastName: String,

    @Enumerated
    var doctorDegree: DoctorDegree = DoctorDegree.NONE,

    @Enumerated
    var academicDegree: AcademicDegree = AcademicDegree.NONE,

    @ManyToMany
    var organisations: MutableList<Organisation>,

    var orcid: String? = null,

    var researchId: String? = null,

    @OneToOne
    val credentials: Credentials

)
