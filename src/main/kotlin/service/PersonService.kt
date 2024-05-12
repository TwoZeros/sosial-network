package ru.kotlin.hunter.service

import ru.kotlin.hunter.model.Person
import ru.kotlin.hunter.model.PersonFullData
import ru.kotlin.hunter.model.Skill
import ru.kotlin.hunter.repository.PersonRepository
import ru.kotlin.hunter.repository.SkillRepository
import ru.kotlin.hunter.repository.RelationRepository

class PersonService(
    private val personRepository: PersonRepository,
    private val skillRepository: SkillRepository,
    private val relationRepository: RelationRepository
) {

    fun getSkill(id: String): String? {
        return skillRepository.findSkillNameById(id)
    }

    fun getPerson(id: String):Person? {
        return personRepository.findPersonById(id)
    }
    fun searchPersonWithNeedSkill(whoSearchId: String, skillName:String): PersonFullData? {
        val searchQueue = ArrayDeque<String>()
        val skillId = skillRepository.getSkillIdByName(skillName) ?: return null
        val viewed = mutableSetOf<String>()
        val personFriend1lvl = getPersonFriend(whoSearchId)
        searchQueue.addAll(personFriend1lvl)
        while (searchQueue.isNotEmpty()) {
            val chekedPerson = searchQueue.removeFirst()
            if (viewed.contains(chekedPerson)) continue
            if(personHasSkill(chekedPerson, skillId ))  {
                return getPersonFullData(chekedPerson)
            } else {
                searchQueue.addAll(getPersonFriend(chekedPerson))
                viewed.add(chekedPerson)
            }
        }
        return null
    }
    fun personHasSkill(personId: String, skillId:String): Boolean {
        val person = getPerson(personId)
        return person?.skills?.contains(skillId) ?: false
    }

    fun getPersonFriend(personId: String): List<String> {
        return relationRepository.findRelationById(personId)?: emptyList()
    }

    fun getPersonFullData(personId: String): PersonFullData? {
        val person =personRepository.findPersonById(personId)?: return null
        return PersonFullData(
            id = person.id,
            name = person.name,
            skills = person.skills.map { Skill(it, skillRepository.findSkillNameById(it)!!) }
        )
    }
}