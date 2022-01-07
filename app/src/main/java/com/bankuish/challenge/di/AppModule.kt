package com.bankuish.challenge.di

import com.bankuish.challenge.data.GitHubProjectRepository
import com.bankuish.challenge.data.GitHubService
import com.bankuish.challenge.data.IGitHubRepository
import com.bankuish.challenge.presentation.GitHubProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@JvmField
val appModule = module {
    // single instance of HelloRepository
    single<IGitHubRepository> { GitHubProjectRepository(get()) }
    single { getServiceInstance() }

    // Simple Presenter Factory
//        factory { MySimplePresenter(get()) }

    // Simple Java Presenter
//        factory { MyJavaPresenter(get()) }

    // scope for MyScopeActivity
//        scope<MyScopeActivity> {
//            // scoped MyScopePresenter instance
//            scoped { MyScopePresenter(get()) }
//        }

    viewModel { GitHubProjectViewModel(get()) }


}

fun getServiceInstance(): GitHubService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(GitHubService::class.java)
}


