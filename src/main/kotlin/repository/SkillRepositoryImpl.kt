package ru.kotlin.hunter.repository

import com.beust.klaxon.Klaxon
import ru.kotlin.hunter.model.Skill

interface SkillRepository {
    fun findSkillNameById(id : String): String?
    fun getSkillIdByName(skillName: String): String?
}

class SkillRepositoryImpl : SkillRepository {

    private val _skill = getDataFromFile()

    override fun findSkillNameById(id: String): String? {
        return _skill[id]
    }

    override fun getSkillIdByName(skillName: String) = _skill.filterValues { it == skillName }.firstNotNullOf { it.key }

    private fun getDataFromFile(): Map<String, String> {
        val text = this.javaClass.classLoader.getResource("skills.json")!!.readText()
        return Klaxon().parseArray<Skill>(text)!!.map{it.id to it.name}.toMap()
    }
}