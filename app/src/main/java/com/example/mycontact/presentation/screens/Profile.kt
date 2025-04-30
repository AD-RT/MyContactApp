package com.example.mycontact.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mycontact.AppState
import com.example.mycontact.ContactViewModel
import com.example.mycontact.R
import com.example.mycontact.data.entity.Contact
import com.example.mycontact.presentation.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profile(state: AppState,viewModel: ContactViewModel= hiltViewModel(),navController: NavController){

            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        navigationIcon = {
                            IconButton(onClick = {  navController.navigateUp() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "null")
                            }
                        },
                        title = {
                            Text("Profile")
                        }
                    )
                }
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Top

                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                        Card(
                            modifier = Modifier
                                .width(380.dp)
                                .height(500.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            ) {
                            Column(
                                modifier = Modifier.
                                fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {

                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(180.dp)
                                    .clip(CircleShape)
                            )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                   verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.Start
                                ){
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        colors = CardDefaults.cardColors(Color.White),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp
                                        )
                                    ){
                                        Row(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ){
                                        Text(text = "Name: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            Text(text =" ${state.name.value}", fontSize = 16.sp)
                                    }
                                    }
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        colors = CardDefaults.cardColors(Color.White),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ) {
                                            Text(text = "Phone: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            Text(text =" ${state.phoneNumber.value}", fontSize = 16.sp)

                                        }
                                    }
                                    Card(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                        colors = CardDefaults.cardColors(Color.White),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp)
                                    )
                                    {
                                        Row(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ) {
                                            Text(text = "Email: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                            Text(text =" ${state.email.value}", fontSize = 16.sp)
                                        }
                                    }
                                }

                        }
                    }
                        Spacer(modifier = Modifier.height(30.dp))

                    deleteProfile(contact = Contact(state.id.value,state.name.value,state.phoneNumber.value,state.email.value), viewModel = viewModel, navController = navController)

                }
            }
}
@Composable
fun deleteProfile(contact: Contact, viewModel: ContactViewModel, navController: NavController){
    Button(
        onClick = {
            viewModel.state.value.id.value = contact.id
            viewModel.state.value.name.value = contact.name
            viewModel.state.value.phoneNumber.value = contact.phoneNumber
            viewModel.state.value.email.value = contact.email
            viewModel.deleteContact()
            navController.navigate(Routes.HomeScreen)
        }
    ) {
        Text(
            text = "Delete Profile",
            fontSize = (16.sp)
        )
    }
}



