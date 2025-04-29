package com.example.githubfinder.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubfinder.R
import com.example.githubfinder.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    title: String, // screen title
    users: List<UserProfile>, // list of users to display
    onBack: () -> Unit, // callback for back navigation
    onUserClick: (UserProfile) -> Unit // callback when a user is clicked
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        title,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(colorResource(id = R.color.white)),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (users.isEmpty()) {
                Text("No ${title.lowercase()} found",
                    modifier = Modifier.padding(8.dp))
            } else {
                LazyColumn {
                    // display each user as a list item
                    items(users) { user ->
                        UserListItem(user) {
                            // call the provided user click handler
                            onUserClick(user)
                        }
                    }
                }
            }
        }
    }
}
