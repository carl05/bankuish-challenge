package com.bankuish.challenge.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories")
    suspend fun getGithubProjects(
        @Query("q") language: String,
        @Query("per_page") resultsPerPage: String,
        @Query("page") pageNumber: String
    ): Response<GitHubProjectResponse>
}