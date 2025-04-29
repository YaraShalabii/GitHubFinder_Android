package com.example.githubfinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.githubfinder.ui.theme.GitHubFinderTheme
import com.example.githubfinder.view.MainScreen
import com.example.githubfinder.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    // lazy initialization of the MainViewModel
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SuspiciousIndentation") // ignores indentation warning
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enables edge-to-edge layout that removes default system bars padding
        enableEdgeToEdge()

        // preload profile if username is passed via intent
        val intentUsername = intent.getStringExtra("username")
        if (!intentUsername.isNullOrEmpty()) {
            // trigger search for the passed username
            viewModel.searchUsername(intentUsername)
        }

        // set the UI content
        setContent {
            GitHubFinderTheme {
                // load the MainScreen Composable and pass the ViewModel and the intent username
                MainScreen(
                    viewModel,
                    intentUsername
                )
            }
        }
    }
}
