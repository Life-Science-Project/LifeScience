package com.jetbrains.life_science.user.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    
    @Column(
        name = "username",
        unique = true
    )
    val username: String,
    
    @Column(name = "first_name")
    val firstName: String,
    
    @Column(name = "last_name")
    val lastName: String,
    
    @Column(name = "email")
    val email: String,
    
    @Column(name = "password")
    val password: String,
    
    @Column(name = "enabled")
    val enabled: Boolean = false,
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableCollection<Role>
)
