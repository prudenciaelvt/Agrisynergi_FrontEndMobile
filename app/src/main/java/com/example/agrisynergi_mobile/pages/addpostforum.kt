package com.example.agrisynergi_mobile.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen

@Composable
fun AddPostScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // AddPostTopBar
        AddPostTopBar(onBackClicked = { navController.popBackStack() })

        Spacer(modifier = Modifier.height(20.dp))

        // AddPostContent
        AddPostContent(
            imageUri = imageUri,
            onImagePicked = { imagePickerLauncher.launch("image/*") },
            onDescriptionChanged = { description = it },
            description = description
        )

        Spacer(modifier = Modifier.weight(1f))

        // Posting Button (with no backend logic)
        Button(
            onClick = {
                if (imageUri != null && description.isNotBlank()) {
                    // Simulate post logic (e.g., show a success message or navigate to the forum)
                    println("Post created with description: $description and image: $imageUri")
                    navController.navigate(Screen.Forum.route) // Simulate navigating to the forum screen
                } else {
                    println("Missing image or description.")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F897B)
            )
        ) {
            Text("Posting", color = Color(0xFFF0F0F0))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text("Tambah Postingan", color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            // Add action items if needed (e.g., save, edit)
        },
        backgroundColor = Color(0xFF13382C)
    )
}



@Composable
fun AddPostContent(
    imageUri: Uri?,
    onImagePicked: () -> Unit,
    onDescriptionChanged: (String) -> Unit,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Button(
                onClick = onImagePicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F897B)
                )
            ) {
                Text("Pilih Gambar", color = Color(0xFFF0F0F0))
            }
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Button(
                onClick = onImagePicked,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F897B)
                )
            ) {
                Text("Ganti Gambar", color = Color(0xFFF0F0F0))
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChanged,
        label = { Text("Tambah Caption") },
        placeholder = { Text("Tulis disini..") },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        maxLines = Int.MAX_VALUE
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPostScreen() {
    // Mock NavController for preview purposes
    val navController = rememberNavController()

    // Call AddPostScreen with the mock navController
    AddPostScreen(navController = navController)
}
