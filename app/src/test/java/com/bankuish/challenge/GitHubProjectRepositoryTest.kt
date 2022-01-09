package com.bankuish.challenge

import com.bankuish.challenge.data.GitHubProjectRepository
import com.bankuish.challenge.data.GitHubProjectResponse
import com.bankuish.challenge.data.GitHubService
import com.bankuish.challenge.data.IGitHubProjectRepository
import com.bankuish.challenge.di.getServiceInstance

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

class GitHubProjectRepositoryTest : KoinTest {
    private val sampleModule = module {
        single<IGitHubProjectRepository> { GitHubProjectRepository(get()) }
        single { getServiceInstance() }
    }

    init {
        startKoin { modules(sampleModule) }
    }

    private val service: GitHubService = mock()
    private val gitHubProjectRepository: IGitHubProjectRepository by inject()


    @Test
    fun `when get projects and have success response`() {
        runBlocking {
            val respGit = GitHubProjectResponse()
            val resp: Response<GitHubProjectResponse> = Response.success(200, respGit)
            Mockito.`when`(service.getGithubProjects("", "", "")).thenReturn(
                resp
            )
            val response = gitHubProjectRepository.getKotlinRepositories("", "", "")
            assert(response.isSuccessful)
            assert(response.code() == 200)
        }
    }

    @Test
    fun `when get projects and have fail response`() {
        val respGit: ResponseBody = mock()
        val resp: Response<GitHubProjectResponse> = Response.error(500, respGit)
        runBlocking {
            Mockito.`when`(service.getGithubProjects("", "", "")).thenReturn(
                resp
            )
            val response = gitHubProjectRepository.getKotlinRepositories("", "", "")
            assert(!response.isSuccessful)
            assert(response.code() == 500)
        }
    }
}