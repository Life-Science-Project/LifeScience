package com.jetbrains.life_science.method.factory

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class MethodFactory {
    fun createMethod(methodInfo: MethodInfo, section: Section): Method {
        // TODO: Добавить создание статьи через сервис по шаблону по умолчанию
        return Method(methodInfo.getId(), methodInfo.getName(), section, Article(0))
    }
}
