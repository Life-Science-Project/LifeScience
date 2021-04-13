package com.jetbrains.life_science.container.search.service

import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.search.factory.ContainerSearchUnitFactory
import com.jetbrains.life_science.container.search.repository.ContainerSearchUnitRepository
import com.jetbrains.life_science.exception.search_unit.ContainerSearchUnitNotFoundException
import org.springframework.stereotype.Service

@Service
class ContainerSearchUnitServiceImpl(
    val repository: ContainerSearchUnitRepository,
    val factory: ContainerSearchUnitFactory
) : ContainerSearchUnitService {

    override fun create(container: Container) {
        val searchUnit = factory.create(container)
        repository.save(searchUnit)
    }

    override fun delete(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(container: Container) {
        checkExistsById(container.id)
        val searchUnit = factory.create(container)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ContainerSearchUnitNotFoundException("Container search unit with id $id not found")
        }
    }
}
