package com.bankuish.challenge

import com.bankuish.challenge.domain.GitHubProject
import com.bankuish.challenge.domain.GitHubProjectUseCase
import com.bankuish.challenge.presentation.GitHubProjectViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class GitHubProjectViewModelUnitTest : KoinTest {
    private val sampleModule = module {
        single { GitHubProjectViewModel(gitHubProjectUseCase) }
    }
    @Before
    fun setUp() {
        startKoin { modules(sampleModule) }
    }

    private val gitHubProjectUseCase: GitHubProjectUseCase = mock()
    private val viewModel: GitHubProjectViewModel by inject()

    @Test
    fun `when get projects and have success response`() {
        runBlocking {
            Mockito.`when`(gitHubProjectUseCase.getKotlinProjects(false)).thenReturn(
                Response.success(emptyList())
            )
            viewModel.getKotlinRepos(false)
            Mockito.verify(viewModel.gitHubLiveData, Mockito.times(1))
        }
    }

    @Test
    fun `when get projects and have fail response`() {
        runBlocking {
            Mockito.`when`(gitHubProjectUseCase.getKotlinProjects()).thenReturn(
                Response.error(500, mock())
            )

            viewModel.getKotlinRepos(false)
            Mockito.verify(viewModel.gitHubLiveData, Mockito.times(0))
        }
    }


}


