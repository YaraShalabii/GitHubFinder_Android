package com.example.githubfinder.view

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.githubfinder.FollowersActivity
import com.example.githubfinder.FollowingActivity
import com.example.githubfinder.MainActivity
import com.example.githubfinder.R
import com.example.githubfinder.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    intentUsername: String?
) {
    // search query state
    var query by remember { mutableStateOf("") }

    // observe LiveData from the MainViewModel
    val user = viewModel.user.observeAsState()
    val error = viewModel.error.observeAsState()

    // holds the currently selected username, initialized with the intent value if available
    // used to update screen topBar title into username of current profile
    var selectedUsername by remember(intentUsername) { mutableStateOf(intentUsername) }

    // get activity context
    val context = LocalContext.current
    val activity = context as? MainActivity

    // flag root check to disable the Arrow Back Button in the root
    val isRoot = activity?.isTaskRoot == true

    Scaffold(
        topBar = {
            // app bar with dynamic title and back navigation
            TopAppBar(
                title = {
                    Text(
                        text = selectedUsername ?: "GitHub Finder",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        colorResource(id = R.color.white)
                    ),
                navigationIcon = {
                    // enable back arrow if not root and if search is active
                    if (!isRoot || query.isNotEmpty()) {
                        IconButton(onClick = {
                            if (!isRoot) {
                                activity?.finish()
                            } else {
                                // clear search bar when back to the main screen (before searching)
                                viewModel.clearUser()
                                selectedUsername = null
                                query = ""
                            }
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }

            )
        },
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // search bar composable function with username search and result selection
            SearchBar(viewModel = viewModel,
                onSearchResultClicked = { username ->
                    selectedUsername = username
                },
                query = query,
                onQueryChange = {
                    query = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // display error if user not found
            error.value?.let {
                Text(
                    text = it,
                    color = MaterialTheme
                        .colorScheme
                        .error
                )
            }

            // preview user profile if found
            user.value?.let { user ->

                // set user's avatar using avatar_url
                AsyncImage(
                    model = user.avatar_url ?: "",
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )

                // set username (login), name, and bio
                Text(text = user.login ?: "", style = MaterialTheme.typography.titleLarge)
                Text(text = user.name ?: "", style = MaterialTheme.typography.titleMedium)
                Text(text = user.bio ?: "", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                // followers and following counts with clickable intents
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(
                            text = "${user.followers}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {

                                // start the FollowersActivity once followers count clicked
                                val intent = Intent(context, FollowersActivity::class.java)
                                // pass the username to fetch user data (followers list)
                                intent.putExtra("username", user.login)
                                context.startActivity(intent)
                            }
                        )
                        Text(
                            text = "followers"
                        )
                    }
                    Column {
                        Text(
                            text = "${user.following}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {

                                // start the FollowersActivity once following count clicked
                                val intent = Intent(context, FollowingActivity::class.java)
                                // pass the username to fetch user data (following list)
                                intent.putExtra("username", user.login)
                                context.startActivity(intent)
                            }
                        )
                        Text(
                            text = "following"
                        )
                    }
                }
            }
        }
    }
}
