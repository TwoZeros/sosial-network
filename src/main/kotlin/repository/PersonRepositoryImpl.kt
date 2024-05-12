package ru.kotlin.hunter.repository;


import com.beust.klaxon.Klaxon
import ru.kotlin.hunter.model.Person


interface PersonRepository {
    fun findPersonById(id: String): Person?

}

class PersonRepositoryImpl : PersonRepository {

    private val _users = getDataFromFile()

    override fun findPersonById(id: String): Person? {
        return _users[id]
    }

    private fun getDataFromFile(): Map<String, Person> {

        val text = this.javaClass.classLoader.getResource("peopleInfo.json")!!.readText()
        return Klaxon()
            .parseArray<Person>(text)!!.map { it.id to it }.toMap()
    }
}
