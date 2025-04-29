package com.example.githubfinder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.githubfinder.view.UserListScreen
import com.example.githubfinder.viewmodel.MainViewModel

class FollowersActivity : ComponentActivity() {

    // lazy initialization of the MainViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // enables edge-to-edge layout that removes default system bars padding
        enableEdgeToEdge()

        // preload profile if username is passed via intent
        val username = intent.getStringExtra("username") ?: return

        // requests followers list from the ViewModel
        viewModel.usernameFollowers(username)

        // set the UI content
        setContent {

            // get activity context
            val context = LocalContext.current

            // observe followers list from ViewModel
            val followers = viewModel.followers.observeAsState(emptyList())

            // display the list of followers using the UserListScreen composable
            UserListScreen(
                // set the title
                title = "Followers",
                // pass the list of followers
                users = followers.value,
                onBack = {
                    // handle back navigation by closing the activity
                    (context as? FollowersActivity)?.finish()
                },
                onUserClick = { user ->
                    // navigate to the MainScreen with the selected username
                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtra("username", user.login)
                    }
                    context.startActivity(intent)
                }
            )
        }
    }
}

