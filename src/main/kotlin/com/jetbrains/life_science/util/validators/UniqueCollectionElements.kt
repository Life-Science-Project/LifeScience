package com.jetbrains.life_science.util.validators

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@MustBeDocumented
@Constraint(validatedBy = [UniqueCollectionElementsValidator::class])
annotation class UniqueCollectionElements(
    val message: String = "Collection elements must be unique",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
