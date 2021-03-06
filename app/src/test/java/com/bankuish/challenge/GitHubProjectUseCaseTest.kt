package com.bankuish.challenge

import com.bankuish.challenge.data.GitHubProjectRepository
import com.bankuish.challenge.data.GitHubProjectResponse
import com.bankuish.challenge.data.GitHubService
import com.bankuish.challenge.data.IGitHubProjectRepository
import com.bankuish.challenge.di.getServiceInstance
import com.bankuish.challenge.domain.GitHubProjectUseCase
import com.bankuish.challenge.presentation.GitHubProjectViewModel
import org.junit.Assert.*

import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class GitHubProjectUseCaseTest : KoinTest {
    private val sampleModule = module {
        single { GitHubProjectUseCase(get()) }
        single<IGitHubProjectRepository> { GitHubProjectRepository(get()) }
        single { getServiceInstance() }
    }


    init {
        startKoin { modules(sampleModule) }
    }

    private val gitHubProjectRepository = GitHubProjectRepository(mock())
    private val gitHubProjectUseCase: GitHubProjectUseCase by inject()


    @Test
    fun `when get projects and have success response`() {
        runBlocking {
            val respGit = GitHubProjectResponse()
            val resp: Response<GitHubProjectResponse> = Response.success(200, respGit)
            Mockito.`when`(gitHubProjectRepository.getKotlinRepositories("", "", "")).thenReturn(
                resp
            )
            val response = gitHubProjectUseCase.getKotlinProjects()
            assert(response.isSuccessful)
            assert(response.code() == 200)
        }
    }

    @Test
    fun `when get projects and have fail response`() {
        runBlocking {
            val respGit: ResponseBody = mock()
            val resp: Response<GitHubProjectResponse> = Response.error(500, respGit)
            Mockito.`when`(gitHubProjectRepository.getKotlinRepositories("", "", "")).thenReturn(
                resp
            )
            val response = gitHubProjectUseCase.getKotlinProjects()
            assert(!response.isSuccessful)
            assert(response.code() == 500)
        }
    }
}
