package com.jetbrains.life_science.util.validators

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueCollectionElementsValidator : ConstraintValidator<UniqueCollectionElements, Collection<*>> {
    override fun isValid(value: Collection<*>?, context: ConstraintValidatorContext?) =
        (value == null) || (value.distinct().size == value.size)
}
