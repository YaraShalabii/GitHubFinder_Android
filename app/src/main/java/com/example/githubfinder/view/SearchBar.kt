package com.example.githubfinder.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.githubfinder.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: MainViewModel,
    onSearchResultClicked: (String) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    // observe the user LiveData from the MainViewModel
    val user by viewModel.user.observeAsState()

    // manages whether the search bar is in an active (focused) state
    var active by remember { mutableStateOf(false) }

    // search bar UI
    SearchBar(
        query = query,
        onQueryChange = {
            onQueryChange(it)

            // starts a search as the user types, if the input is not blank
            if (it.isNotBlank()) {
                viewModel.searchUsername(it)
            }
        },
        onSearch = {

            // triggered when the user submits the search
            if (query.isNotBlank()) {
                viewModel.searchUsername(query)
                active = false
            }
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text("Search username") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.Transparent,
            dividerColor = Color.DarkGray
        )
    ) {

        // displays search result if user is found
        user?.let { user ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSearchResultClicked(user.login ?: "")
                        active = false
                    }
                    .padding(8.dp)
            ) {
                Text(text = user.login ?: "", fontWeight = FontWeight.Bold)
                user.name?.let { Text(it) }
            }
        } ?: run {
            // shows fallback messages based on query state
            if (query.isEmpty()) {
                Text("Search username", modifier = Modifier.padding(8.dp))
            } else if (user == null) {
                Text("User not found", modifier = Modifier.padding(8.dp))
            }

        }
    }
}
