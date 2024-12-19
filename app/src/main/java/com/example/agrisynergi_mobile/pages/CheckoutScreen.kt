package com.example.agrisynergi_mobile.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(marketId: Int, navController: NavController) {
    var isOrderComplete by remember { mutableStateOf(false) }

    if (!isOrderComplete) {
        Scaffold(
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(colorResource(R.color.hijau_tua)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_leftleft),
                            contentDescription = null,
                            tint = colorResource(R.color.white)
                        )
                    }

                    Text(
                        text = "CHECKOUT",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = colorResource(R.color.white)
                    )

                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_shopping_bag_r),
                            contentDescription = null,
                            tint = colorResource(R.color.white)
                        )
                    }
                }
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                        .background(colorResource(R.color.white))
                        .fillMaxWidth()
                        .shadow(elevation = 0.1.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Button(
                            onClick = { isOrderComplete = true },
                            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.hijau_pudar)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Buat Pesananan")
                        }
                    }
                }
            }
        ) { paddingValues ->
            CheckoutItemsScreen(marketId, Modifier.padding(paddingValues))
        }
    } else {
        Scaffold(
            topBar = {
                DoneCheckoutHeader(onBackPressed = {
                    navController.navigateUp()
                })
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                DoneCheckoutContent()
            }
        }
    }
}

@Composable
fun CheckoutItemsScreen(marketId: Int, modifier: Modifier){
    val market = datamarket.markets.find { it.id == marketId }
    Column(modifier = modifier.padding(20.dp).fillMaxSize()) {
        Box {
            Column(modifier = Modifier.fillMaxWidth().border(color = Color.Green, width = 1.dp, shape = RoundedCornerShape(corner = CornerSize(8.dp)))
                .clip(RoundedCornerShape(8.dp)) // Sudut bulat dengan radius 16dp
                .background(colorResource(R.color.white))
                .padding(10.dp)) {
                Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                    Box(){
                        Row {
                            Image(
                                painter = painterResource(R.drawable.ic_map_r),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(text = "Alamat Pengiriman", fontSize = 16.sp, softWrap = true)
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leftleft),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                }
                Text(text = """Perumahan Setu Indah (+621823456789)
Perumahan Setu Indah, Jalan Kenangan Setu Indah, No.14c RT 001/02, Bogor, Jakarta Selatan DKI Jakarta, ID : 12450""", fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(6.dp))
        Box {
            Column(modifier = Modifier.fillMaxWidth().border(color = Color.Green, width = 1.dp, shape = RoundedCornerShape(corner = CornerSize(8.dp)))
                .clip(RoundedCornerShape(8.dp)) // Sudut bulat dengan radius 16dp
                .background(colorResource(R.color.white))
                .padding(10.dp)){
                Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                    Box(){
                        Row {
                            Image(
                                painter = painterResource(R.drawable.ic_tas_r),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(text = market!!.title, fontSize = 16.sp, softWrap = true)
                                Text(text = market.price, fontSize = 12.sp)
                            }
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leftleft),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                }
            }
        }
        Spacer(Modifier.height(6.dp))
//        Box {
//            Column(modifier = Modifier.fillMaxWidth().border(color = Color.Green, width = 1.dp, shape = RoundedCornerShape(corner = CornerSize(8.dp)))
//                .clip(RoundedCornerShape(8.dp)) // Sudut bulat dengan radius 16dp
//                .background(colorResource(R.color.white))
//                .padding(10.dp)) {
//                Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
//                    Box(){
//                        Row {
//                            Image(
//                                painter = painterResource(R.drawable.ic_gift_r),
//                                contentDescription = null
//                            )
//                            Spacer(Modifier.width(12.dp))
//                            Text(text = "Add discount code", fontSize = 16.sp, softWrap = true)
//                        }
//                    }
//                    IconButton(onClick = {}) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_leftleft),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(20.dp)
//                                .graphicsLayer(rotationZ = 268f), // Rotasi ikon 45 derajat // Atur warna ikon jika perlu
//                        )
//                    }
//
//                }
//            }

        Spacer(Modifier.height(6.dp))
        Box(modifier = Modifier// Ukuran Box
            .clip(RoundedCornerShape(8.dp)) // Sudut bulat dengan radius 16dp
            .background(colorResource(R.color.hijau_pudar)) ) {
            Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                    Box(){
                        Row {
                            Spacer(Modifier.width(12.dp))
                            Text(text = "Items summary", fontSize = 16.sp, softWrap = true, fontWeight = FontWeight.Bold, color = colorResource(R.color.white))
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_leftleft),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer(rotationZ = 268f),
                            tint = colorResource(R.color.white)// Rotasi ikon 45 derajat // Atur warna ikon jika perlu
                        )
                    }

                }
            }
        }
        Spacer(Modifier.height(15.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            Box(){
                Row {
                    Text(text = "Subtotal untuk Produk", fontSize = 12.sp, softWrap = true)
                }
            }
            Text(text = "Rp.35.000", fontSize = 12.sp, softWrap = true, fontWeight = FontWeight.Bold)

        }
        Spacer(Modifier.height(10.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            Box(){
                Row {
                    Text(text = "Subtotal Pengiriman", fontSize = 12.sp, softWrap = true)
                }
            }
            Text(text = "Rp.10.000", fontSize = 12.sp, softWrap = true, fontWeight = FontWeight.Bold)

        }
        Spacer(Modifier.height(10.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            Box(){
                Row {
                    Text(text = "Biaya Layanan", fontSize = 12.sp, softWrap = true)
                }
            }
            Text(text = "Rp.1.370", fontSize = 12.sp, softWrap = true, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(50.dp))
        Box (modifier = Modifier.fillMaxWidth().background(colorResource(R.color.black)).height(1.dp))
        Spacer(Modifier.height(2.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            Box(){
                Row {
                    Text(text = "Total", fontSize = 18.sp, softWrap = true, fontWeight = FontWeight.Bold)
                }
            }
            Text(text = "Rp.46.370", fontSize = 18.sp, softWrap = true, fontWeight = FontWeight.Bold)
        }

    }

}

@Composable
fun DoneCheckoutHeader(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Arrow",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackPressed() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Check Out",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun DoneCheckoutContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.order_complete),
                contentDescription = "Order Complete Image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Berhasil membuat pesanan!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Silakan tunggu barang diantarkan ke tempat anda.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
            ) {
                Text("Kembali", color = Color.White)
            }
        }
    }
}