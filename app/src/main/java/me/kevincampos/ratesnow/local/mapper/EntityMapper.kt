package me.kevincampos.ratesnow.local.mapper

interface EntityMapper<Entity, Domain> {

    fun mapFromEntity(entity: Entity): Domain

    fun mapToEntity(domain: Domain): Entity

}
