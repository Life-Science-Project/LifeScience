package com.jetbrains.life_science.validator

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.security.access.AccessDeniedException

inline fun validateUserAndVersionToEdit(
    version: ArticleVersion,
    user: User,
    lazyMessage: (() -> String)
) {
    if (version.isPublished || !version.canModify(user)) {
        throw AccessDeniedException(lazyMessage())
    }
}
