package me.kevincampos.ratesnow.remote.mapper

interface ResponseMapper<in Response, out Domain> {

    fun mapToDomain(response: Response): Domain

}