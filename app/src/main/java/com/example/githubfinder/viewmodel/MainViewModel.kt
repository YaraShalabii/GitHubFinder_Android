package com.example.githubfinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubfinder.model.User
import com.example.githubfinder.repository.GitHubRepository
import androidx.lifecycle.viewModelScope
import com.example.githubfinder.model.UserProfile
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // instantiate the repository which contains the logic to fetch data from the API
    private val repository = GitHubRepository()

    // LiveData to hold user information, followers, following, and error messages
    val user = MutableLiveData<User?>()
    val followers = MutableLiveData<List<UserProfile>>()
    val following = MutableLiveData<List<UserProfile>>()
    val error = MutableLiveData<String?>()

    // function to search a user by username
    fun searchUsername(username: String) {
        // clear any previous user-related data
        clearUser()
        viewModelScope.launch {
            try {
                // fetch the user profile from the repository
                val userResult = repository.getUser(username)

                // update the user LiveData with the result
                user.value = userResult

                // clear any previous error
                error.value = null
            } catch (e: Exception) {
                // handle the exception if the user is not found or an error occurs
                error.value = "User not found"

                // reset the user data if there's an error
                clearUser()
            }
        }
    }

    // function to fetch followers for a given username
    fun usernameFollowers(username: String) {
        viewModelScope.launch {
            try {
                // fetch followers from the repository
                val followersResult = repository.getFollowers(username)
                followers.value = followersResult
            } catch (e: Exception) {
                // handle the exception if there is an error fetching followers
                error.value = "Failed to load followers"
            }
        }
    }

    // function to fetch following for a given username
    fun usernameFollowing(username: String) {
        viewModelScope.launch {
            try {
                // fetch following from the repository
                val followingResult = repository.getFollowing(username)
                following.value = followingResult
            } catch (e: Exception) {
                // handle the exception if there is an error fetching following
                error.value = "Failed to load following"
            }
        }
    }

    // clear user data, followers, following, and error
    fun clearUser() {
        user.postValue(null)  // clear user data
        followers.postValue(emptyList())  // clear followers data
        following.postValue(emptyList())  // clear following data
        error.postValue(null)  // clear any previous error messages
    }
}
