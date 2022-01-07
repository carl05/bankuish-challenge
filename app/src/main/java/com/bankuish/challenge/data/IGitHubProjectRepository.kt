package com.bankuish.challenge.data

import retrofit2.Response

interface IGitHubProjectRepository{

    fun getKotlinRepositories(language: String,
                              resultsPerPage: String,
                              pageNumber: String): Response<GitHubProjectResponse>
}