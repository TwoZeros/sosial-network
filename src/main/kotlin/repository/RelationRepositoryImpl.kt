package ru.kotlin.hunter.repository

import com.beust.klaxon.Klaxon
import ru.kotlin.hunter.model.PersonRelation

interface RelationRepository {
    fun findRelationById(id : String): List<String>?
}

class RelationRepositoryImpl : RelationRepository {

    private val _relation = getDataFromFile()

    override fun findRelationById(id: String): List<String>? {
        return _relation[id]
    }

    private fun getDataFromFile(): Map<String, List<String>> {
        val text = this.javaClass.classLoader.getResource("peopleRelation.json")!!.readText()
        return Klaxon().parseArray<PersonRelation>(text)!!.map{it.id to it.friends}.toMap()
    }
}