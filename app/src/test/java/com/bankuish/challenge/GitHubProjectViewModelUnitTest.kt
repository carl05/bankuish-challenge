package com.challenge

import com.bankuish.challenge.data.GitHubProjectRepository
import com.bankuish.challenge.data.GitHubProjectResponse
import com.bankuish.challenge.data.IGitHubProjectRepository
import com.bankuish.challenge.di.getServiceInstance
import com.bankuish.challenge.domain.GitHubProjectUseCase
import com.bankuish.challenge.presentation.GitHubProjectViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import retrofit2.Response

class GitHubProjectViewModelUnitTest : KoinTest {
    private val sampleModule = module {
        single { GitHubProjectViewModel(get()) }
        single { GitHubProjectUseCase(get()) }
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

    private val gitHubProjectUseCase = GitHubProjectUseCase(mock())

    private val viewModel: GitHubProjectViewModel by inject()

    @Test
    fun `when get projects and have success response`() {
        runBlocking {
            `when`(gitHubProjectUseCase.getKotlinProjects()).thenReturn(
                Response.success(emptyList())
            )

            viewModel.getKotlinRepos()
            Mockito.verify(viewModel.projectList, Mockito.times(1))
        }
    }
    @Test
    fun `when get projects and have fail response`() {
        runBlocking {
            `when`(gitHubProjectUseCase.getKotlinProjects()).thenReturn(
                Response.error(200, mock())
            )

            viewModel.getKotlinRepos()
            Mockito.verify(viewModel.projectList, Mockito.times(0))
        }
    }


}


