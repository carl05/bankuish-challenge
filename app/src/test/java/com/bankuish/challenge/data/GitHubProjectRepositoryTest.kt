package com.bankuish.challenge.data

import com.bankuish.challenge.di.getServiceInstance
import com.bankuish.challenge.domain.GitHubProjectUseCase
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
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class GitHubProjectRepositoryTest : KoinTest {
    private val sampleModule = module {
        single { getServiceInstance() }
    }
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    init {
        startKoin { modules(sampleModule) }
    }

    private val service: GitHubService = mock()
    private val gitHubProjectRepository: GitHubProjectRepository by inject()


    @Test
    fun `when get projects and have success response`() {
        val respGit: GitHubProjectResponse = mock()
        val resp: Response<GitHubProjectResponse> = Response.success(200, respGit)
        runBlocking {
            Mockito.`when`(service.getGithubProjects("", "", "")).thenReturn(
                resp
            )
            val response = gitHubProjectRepository.getKotlinRepositories("", "", "")
            Mockito.verify(response.isSuccessful, Mockito.times(1))
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
            Mockito.verify(!response.isSuccessful, Mockito.times(1))
        }
    }
}