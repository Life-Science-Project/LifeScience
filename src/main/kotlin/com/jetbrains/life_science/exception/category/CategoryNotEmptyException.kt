package com.jetbrains.life_science.exception.category

import com.jetbrains.life_science.exception.common.WrongRequestWithMessageException

class CategoryNotEmptyException(id: Long) :
    WrongRequestWithMessageException("Category with id \"$id\" is not empty and can not be deleted")
