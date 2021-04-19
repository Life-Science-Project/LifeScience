package com.jetbrains.life_science.user.credentials.entity

import com.jetbrains.life_science.user.details.entity.User
import javax.persistence.*

@Entity
@Table(name = "user_credential")
class UserCredentials(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(
        name = "username",
        unique = true
    )
    val email: String,

    @Column(name = "password")
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableCollection<Role>,

) {

    @OneToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    lateinit var user: User
}
