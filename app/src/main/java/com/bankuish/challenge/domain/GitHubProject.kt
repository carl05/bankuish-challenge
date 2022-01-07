package com.bankuish.challenge.domain

data class GitHubProject(
    val allow_forking: Boolean? = false,
    val archive_url: String? = "",
    val assignees_url: String? = "",
    val created_at: String? = "",
    val description: String? = "",
    val disabled: Boolean? = false,
    val full_name: String? = "",
    val homepage: String? = "",
    val id: Int? = 0,
    val language: String? = "",
    val name: String? = "",
    val score: Int? = 0,
    val size: Int? = 0,
    val stargazers_count: Int? = 0,
    val stargazers_url: String? = "",
    val topics: List<String>? = emptyList(),
    val updated_at: String? = "",
    val url: String? = "",
    val watchers: Int? = 0,
    val watchers_count: Int? = 0,
    val owner: GitHubOwner? = GitHubOwner()
)

data class GitHubOwner(
    val avatar_url: String? = "",
    val gravatar_id: String? = "",
    val id: Int? = 0,
    val login: String? = "",
    val repos_url: String? = "",
    val type: String? = "",
    val url: String? = ""
)

