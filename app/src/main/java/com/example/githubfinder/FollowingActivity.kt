package com.example.githubfinder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.githubfinder.view.UserListScreen
import com.example.githubfinder.viewmodel.MainViewModel

class FollowingActivity : ComponentActivity() {

    // lazy initialization of the MainViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enables edge-to-edge layout that removes default system bars padding
        enableEdgeToEdge()

        // preload profile if username is passed via intent
        val username = intent.getStringExtra("username") ?: return

        // requests following list from the ViewModel
        viewModel.usernameFollowing(username)

        // set the UI content
        setContent {

            // get activity context
            val context = LocalContext.current

            // observe following list from ViewModel
            val following = viewModel.following.observeAsState(emptyList())

            // display the list of following using the UserListScreen composable
            UserListScreen(
                // set the title
                title = "Following",
                // pass the list of following
                users = following.value,
                onBack = {
                    // handle back navigation by closing the activity
                    (context as? FollowingActivity)?.finish()
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


