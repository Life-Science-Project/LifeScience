package com.jetbrains.life_science.user.data.entity

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import com.jetbrains.life_science.user.organisation.entity.Organisation
import javax.persistence.*

@Entity
@Table(name = "users")
class UserPersonalData(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_personal_data_seq"
    )
    @SequenceGenerator(
        name = "user_personal_data_seq",
        allocationSize = 1
    )
    val id: Long,

    var firstName: String,

    var lastName: String,

    var doctorDegree: Boolean? = false,

    @Enumerated
    var academicDegree: AcademicDegree = AcademicDegree.NONE,

    @ManyToMany
    var organisations: MutableList<Organisation>,

    var orcid: String? = null,

    var researchId: String? = null,

    var about: String? = null,

    @OneToOne(
        cascade = [CascadeType.PERSIST],
        fetch = FetchType.LAZY
    )
    var favoriteGroup: FavoriteGroup,

    @OneToOne(mappedBy = "userPersonalData")
    val credentials: Credentials

)
