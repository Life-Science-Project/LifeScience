package com.jetbrains.life_science.user.master.entity

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation
import com.jetbrains.life_science.user.position.entity.Position
import javax.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    // Credentials
    val email: String,

    val password: String,

    var refreshToken: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableCollection<Role>,

    // Other data
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

    @ManyToMany
    val favouriteArticles: MutableList<ArticleVersion>,

    var orcid: String? = null,

    var researchId: String? = null

) {
    fun isAdminOrModerator(): Boolean {
        return roles.any { it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR" }
    }
}
