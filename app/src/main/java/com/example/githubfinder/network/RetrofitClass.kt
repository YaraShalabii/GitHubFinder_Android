package com.example.githubfinder.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton that creates an implementation of the GitHubAPI interface to make API calls
object RetrofitClass {

    // base URL for the GitHubAPI
    private const val GITHUB_BASE_URL = "https://api.github.com/"

    // lazy initialization of the Retrofit API instance
    val api: GitHubAPI by lazy {
        Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL) // set the base URL for API requests
            .addConverterFactory(GsonConverterFactory.create()) // add Gson converter that parsing JSON
            .build() // build the retrofit instance
            .create(GitHubAPI::class.java) // create the API implementation
    }
}