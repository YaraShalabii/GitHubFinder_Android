package com.example.githubfinder.repository

import com.example.githubfinder.network.RetrofitClass

// a mediator repository calls between ViewModel and the API
class GitHubRepository {
    private val api = RetrofitClass.api

    // fetches the user profile data from GitHub using username
    suspend fun getUser(username: String) = api.getUser(username)

    // fetches the user followers list from GitHub using username
    suspend fun getFollowers(username: String) = api.getFollowers(username)

    // fetches the user following list from GitHub using username
    suspend fun getFollowing(username: String) = api.getFollowing(username)
}