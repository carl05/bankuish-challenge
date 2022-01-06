package com.bankuish.challenge.di

import com.bankuish.challenge.presentation.GitHubRepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@JvmField
val appModule = module {
    // single instance of HelloRepository
//        single<HelloRepository> { HelloRepositoryImpl() }

    // Simple Presenter Factory
//        factory { MySimplePresenter(get()) }

    // Simple Java Presenter
//        factory { MyJavaPresenter(get()) }

    // scope for MyScopeActivity
//        scope<MyScopeActivity> {
//            // scoped MyScopePresenter instance
//            scoped { MyScopePresenter(get()) }
//        }

        viewModel { GitHubRepoViewModel() }
}
