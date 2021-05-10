package com.jetbrains.life_science.validator

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.security.access.AccessDeniedException

inline fun validateUserAndVersion(
    version: ArticleVersion,
    user: User,
    lazyMessage: (() -> String)
) {
    if (!version.canRead(user)) {
        throw AccessDeniedException(lazyMessage())
    }
}
