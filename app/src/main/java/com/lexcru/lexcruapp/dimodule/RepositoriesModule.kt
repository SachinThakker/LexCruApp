package com.lexcru.lexcruapp.dimodule

import com.lexcru.lexcruapp.dimodule.repositories.*
import org.koin.dsl.module

val repositoriesModule = module {
    single { AuthRepository(get()) }
    single { ProfileRepository(get()) }
//    single { NotificationRepository(get()) }
//    single { FavoriteBookRepository(get()) }
//    single { HomeScreenRepository(get()) }
//    single { BookInterpretationRepository(get()) }
//    single { BookReviewRepository(get()) }
//    single { BookRepository(get()) }
//    single { SearchBookRepository(get()) }
//    single { PodcastRepository(get()) }
//    single { PaymentRepository(get()) }

}