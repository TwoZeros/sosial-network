package ru.kotlin.hunter

import org.koin.core.context.GlobalContext.startKoin


fun main() {
    startKoin {
        modules(appModule)
    }

    SocialApp().run()
}