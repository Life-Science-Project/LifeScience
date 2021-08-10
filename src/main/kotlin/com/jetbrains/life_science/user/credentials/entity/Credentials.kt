package com.jetbrains.life_science.user.credentials.entity

import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "credentials")
class Credentials(

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "credentials_seq"
    )
    @SequenceGenerator(
        name = "credentials_seq",
        allocationSize = 1
    )
    val id: Long,

    val email: String,

    private val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableCollection<Role>

) : UserDetails {

    @OneToOne(
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        fetch = FetchType.LAZY
    )
    var userPersonalData: UserPersonalData? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
    }

    override fun getPassword() = password

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    fun isAdminOrModerator(): Boolean {
        return roles.any { it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR" }
    }
}
