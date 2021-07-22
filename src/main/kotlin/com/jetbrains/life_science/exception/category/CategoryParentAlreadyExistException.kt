package com.jetbrains.life_science.exception.category

import com.jetbrains.life_science.exception.common.WrongRequestWithMessageException

class CategoryParentAlreadyExistException(private val parentId: Long, private val childId: Long) :
    WrongRequestWithMessageException("Category with id \"$childId\" already has parent category with id \"$parentId\"")
