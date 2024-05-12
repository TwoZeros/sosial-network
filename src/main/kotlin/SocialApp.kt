package ru.kotlin.hunter

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import ru.kotlin.hunter.repository.PersonRepository
import ru.kotlin.hunter.repository.PersonRepositoryImpl
import ru.kotlin.hunter.repository.RelationRepository
import ru.kotlin.hunter.repository.SkillRepository
import ru.kotlin.hunter.repository.SkillRepositoryImpl
import ru.kotlin.hunter.repository.RelationRepositoryImpl
import ru.kotlin.hunter.service.PersonService

val appModule = module {
    single<PersonRepository> { PersonRepositoryImpl() }
    single<SkillRepository> { SkillRepositoryImpl() }
    single<RelationRepository> {RelationRepositoryImpl()}
    single { PersonService(get(), get(), get()) }
}
class SocialApp: KoinComponent {
    private val personService : PersonService by inject()

    // display our data
    fun run(){

        val design = personService.searchPersonWithNeedSkill("1","design")
        println(design)
    }
}
