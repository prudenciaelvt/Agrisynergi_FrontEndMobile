package com.example.agrisynergymobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Forum
import com.example.agrisynergi_mobile.data.dataforum
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.pages.MainScreen

//Main Forum
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(navController: NavHostController) {
    val forums = dataforum.forums // Ambil data forum
    var showBottomSheet by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(), // Mengisi seluruh layar
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            // Custom Top Bar
            TopBarForum(
                onBackClick = { navController.navigateUp() },
                onSearchClick = { query -> /* Handle search query */ },
                navController = navController,
                onAddClick = { navController.navigate(Screen.AddPost.route) }
            )

            // List forum
            ForumList(
                forums = dataforum.forums,
                onCommentClick = { showBottomSheet = true } // Show the bottom sheet on comment click
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false } // Dismiss on outside click
            ) {
                CommentBottomSheetContent() // Use a placeholder ID if necessary
            }
        }
    }
}




//Setting list forum
@Composable
fun ForumList(
    forums: List<Forum>,
    onCommentClick: (Forum) -> Unit // Pass the specific `Forum` item when clicking
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Ensure it fills the parent
        contentPadding = PaddingValues(vertical = 8.dp) // Add padding for spacing
    ) {
        items(forums) { forum ->
            ForumItem(
                forum = forum,
                onCommentClick = { onCommentClick(forum) } // Pass the clicked forum item
            )
            Divider( // Add a divider between items
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


//Setting Forum Box
@Composable
fun ForumItem(forum: Forum,   onCommentClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    val word = forum.text.split(" ")

    var isBookmarked by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isDisLiked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp)
            ) {
                Row (
                    modifier = Modifier
                        .padding(8.dp)
                ){
                    Image(
                        painter = rememberImagePainter(
                            data = forum.user.profilePic
                        ),
                        contentDescription = "Profile Pic",
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = forum.user.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,

                                )
                        )
                        Text(
                            text = forum.user.username,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = forum.date,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                        if(isExpanded){
                            Text(
                                text = forum.text,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Baca lebih sedikit",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable { isExpanded = false }
                            )
                        } else {
                            Text(
                                text = word.take(30).joinToString(" ") + "...",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Baca selengkapnya",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable { isExpanded = true }
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }

                if (forum.hasImage) {
                    forum.imageUrl?.let {
                        Spacer(modifier = Modifier.height(3.dp))
                        Image(
                            painter = rememberImagePainter(
                                data = it
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .aspectRatio(16/9f),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                ) {
                    IconButton(onClick = {
                        if (!isLiked) {
                            isLiked = true
                            isDisLiked = false // Ensure Dislike is deactivated when Like is clicked
                        } else {
                            isLiked = false // Toggle Like off if already active
                        }
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (isLiked) Color.Red else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "40",
                                color = if (isLiked) Color.Red else Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }

                    IconButton(onClick = {
                        if (!isDisLiked) {
                            isDisLiked = true
                            isLiked = false // Ensure Like is deactivated when Dislike is clicked
                        } else {
                            isDisLiked = false // Toggle Dislike off if already active
                        }
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isDisLiked) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
                                contentDescription = "Dislike",
                                tint = if (isDisLiked) Color.Blue else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "40",
                                color = if (isDisLiked) Color.Blue else Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }

                    IconButton(onClick = onCommentClick) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Comment,
                                contentDescription = "Comment",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "45",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }

                    IconButton(onClick = {
                        isBookmarked = !isBookmarked
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmarks,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) Color(0xFF13382C) else Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "400",
                                color = if (isBookmarked) Color(0xFF13382C) else Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CommentBottomSheetContent() {
    var komentarBaru by remember { mutableStateOf("") }
    var komentarList by remember { mutableStateOf(listOf<String>()) } // Local list of comments
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .height(500.dp)
            .windowInsetsPadding(WindowInsets.ime) // Adjust for the keyboard
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Komentar",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Comments List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (komentarList.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada komentar",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                items(komentarList) { komentar ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = komentar,
                            style = TextStyle(fontSize = 14.sp, color = Color.Black),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        // Comment Input Field
        OutlinedTextField(
            value = komentarBaru,
            onValueChange = { komentarBaru = it },
            placeholder = { Text("Tambahkan komentar...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                IconButton(onClick = {
                    if (komentarBaru.isNotBlank()) {
                        komentarList = komentarList + komentarBaru // Add the new comment to the list
                        komentarBaru = ""
                        errorMessage = ""
                    } else {
                        errorMessage = "Komentar tidak boleh kosong"
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Comment",
                        tint = Color(0xFF13382C)
                    )
                }
            },
            singleLine = true
        )

        // Display Error Message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

//Setting Top Bar
@Composable
fun TopBarForum(
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onAddClick: () -> Unit,
    navController: NavHostController
) {
    Column {
        // Top Bar Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF13382C)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }

            IconButton(onClick = { navController.navigate(Screen.Komunitas.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconcommunity),
                    contentDescription = "Community",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        // Search Bar
        SearchBarForum(onSearchClick = onSearchClick, navController = navController)

        // Tags Section (optional)
        TagLineForum()
    }
}

@Composable
fun SearchBarForum(onSearchClick: (String) -> Unit, navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = "Search...",
                    style = TextStyle(color = Color.Gray)
                )
            },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = {
            navController.navigate(Screen.AddPost.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.iconaddfor),
                contentDescription = "Add",
                tint = Color.Unspecified,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Composable
fun TagLineForum(){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        val categories = listOf("#Trending", "#Terbaru", "#Hamajagung", "#Musim", "#BibitJagung", "#Hama", "Panen")
        items(categories) { category ->
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = category, color = Color(0xFF5B8C51), fontSize = 10.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MainScreenPreview() {
    ForumScreen(navController = rememberNavController())
}



