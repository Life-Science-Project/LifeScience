package com.jetbrains.life_science.method.search.service

import com.jetbrains.life_science.exceptions.search_unit.MethodSearchUnitNotFoundException
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.search.MethodSearchUnit
import com.jetbrains.life_science.method.search.factory.MethodSearchUnitFactory
import com.jetbrains.life_science.method.search.repository.MethodSearchUnitRepository
import org.springframework.stereotype.Service

@Service
class MethodSearchUnitServiceImpl(
    val factory: MethodSearchUnitFactory,
    val repository: MethodSearchUnitRepository
) : MethodSearchUnitService {

    override fun create(method: Method): MethodSearchUnit {
        val searchUnit = factory.create(method)
        return repository.save(searchUnit)
    }

    override fun delete(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(method: Method) {
        checkExistsById(method.id)
        val searchUnit = factory.create(method)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw MethodSearchUnitNotFoundException("Method search unit with id $id not found");
        }
    }

}
