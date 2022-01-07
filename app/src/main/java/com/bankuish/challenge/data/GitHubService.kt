package com.bankuish.challenge.data

import retrofit2.Response
import retrofit2.http.GET

interface GitHubService {

    @GET("search/repositories?q=kotlin&per_page=20&page=1")
    suspend fun getGithubProjects() : Response<GitHubProjectResponse>
}