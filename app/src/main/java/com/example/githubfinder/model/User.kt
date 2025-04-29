package com.example.githubfinder.model

data class User(
    val login: String?, // GitHub unique username
    val name: String?, // the full name of user
    val avatar_url: String?, // the URL of the user's avatar image
    val bio: String?, // a brief description provided by the user
    val followers: Int?, // total count of the profiles that the user is following
    val following: Int? // total count of profile that follows the user
)