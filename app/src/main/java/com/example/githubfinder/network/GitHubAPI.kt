package com.example.githubfinder.network
import com.example.githubfinder.model.User
import com.example.githubfinder.model.UserProfile

import retrofit2.http.GET
import retrofit2.http.Path

// defines the API endpoints for fetching GitHub user information
interface GitHubAPI {
    // fetches a GitHub user information by unique username
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): User

    // fetches a GitHub user followers list by unique username
    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<UserProfile>

    // fetches a GitHub user following list by unique username
    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<UserProfile>
}