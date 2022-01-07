package com.bankuish.challenge.domain

import com.bankuish.challenge.data.GitHubProjectResponse
import com.bankuish.challenge.data.Item
import com.bankuish.challenge.data.Owner

fun Item.map(): GitHubProject {
    return GitHubProject(
        allow_forking = this.allow_forking,
        archive_url = this.archive_url,
        assignees_url = this.assignees_url,
        created_at = this.created_at,
        description = this.description,
        disabled = this.disabled,
        full_name = this.full_name,
        homepage = this.homepage,
        id = this.id,
        language = this.language,
        name = this.name,
        score = this.score,
        size = this.size,
        stargazers_count = this.stargazers_count,
        stargazers_url = this.stargazers_url,
        topics = this.topics,
        updated_at = this.updated_at,
        url = this.url,
        watchers = this.watchers,
        watchers_count = this.watchers_count,
        owner = this.owner.map()
    )
}

fun GitHubProjectResponse.map(): List<GitHubProject> {
    val projects = ArrayList<GitHubProject>()
    this.items.forEach {
        projects.add(it.map())
    }
    return projects
}

fun Owner.map(): GitHubOwner{
    return GitHubOwner(
        avatar_url = this.avatar_url,
        gravatar_id = this.gravatar_id,
        id = this.id,
        login = this.login,
        repos_url = this.repos_url,
        type = this.type,
        url = this.url
    )
}