package com.example.githubfinder.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.githubfinder.model.UserProfile

@Composable
fun UserListItem(
    following: UserProfile, // parameter to hold the passed data for the user to be displayed
    onClick: () -> Unit // callback function for handling click events
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()  // fill the available width
            .padding(8.dp)  // apply padding around the row
            .clickable {  // make the row clickable
                onClick()  // trigger the onClick callback when clicked
            },
        horizontalArrangement = Arrangement.Start,  // align items to the start of the row
        verticalAlignment = Alignment.CenterVertically  // align items vertically in the center
    ) {

        // display user avatar as an image with a rounded shape
        Image(
            painter = rememberAsyncImagePainter(following.avatar_url), // load the image
            contentDescription = null,
            contentScale = ContentScale.Crop, // crop the image to fit inside the circular shape
            modifier = Modifier
                .size(50.dp) // size of the avatar
                .clip(CircleShape) // make the image circular
        )
        Spacer(modifier = Modifier.width(10.dp)) // spacing between image and text
        // display the username as text, falling back to "Unknown" if not provided
        Text(
            following.login ?: "Unknown",
            modifier = Modifier
                .fillMaxWidth()
        )
    }

}