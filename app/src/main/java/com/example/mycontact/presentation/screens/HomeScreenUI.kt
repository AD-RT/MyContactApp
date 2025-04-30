package com.example.mycontact.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mycontact.AppState
import com.example.mycontact.ContactViewModel
import com.example.mycontact.R
import com.example.mycontact.SortOrder
import com.example.mycontact.data.entity.Contact
import com.example.mycontact.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    modifier: Modifier,
    state: AppState,
    viewModel: ContactViewModel= hiltViewModel(),
    navController: NavController
){
    val state by viewModel.state.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                        navigationIcon = {
                            Image(
                                modifier = Modifier
                                    .padding(10.dp),
                                painter = painterResource(id = R.drawable.u),
                                contentDescription = null)
                        },

                        title = {
                            Text("Contacts ",
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Medium,
                                )
                        },
                        actions = {
                            IconButton(onClick = { showMenu = !showMenu }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_sort_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(28.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ){
                                DropdownMenuItem(text = {Text("Sort Ascending")},
                                    onClick = {
                                        viewModel.toggleSortOrder(SortOrder.ASCENDING)
                                        showMenu = false
                                    }
                                )
                                DropdownMenuItem(text = {Text("Sort Descending")},
                                    onClick = {
                                        viewModel.toggleSortOrder(SortOrder.DESCENDING)
                                        showMenu = false
                                    }
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                },

        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.AddContactUI)}) {
                Icon(Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(

        ) {
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(state.AllContacts) {
                    ContactItemUI(
                        contact = it,
                        viewModel = viewModel,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactItemUI(contact: Contact, viewModel: ContactViewModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .combinedClickable (
                onClick = {
                    viewModel.state.value.id.value = contact.id
                    viewModel.state.value.name.value = contact.name
                    viewModel.state.value.phoneNumber.value = contact.phoneNumber
                    viewModel.state.value.email.value = contact.email
                            navController.navigate(Routes.profile)
                          },
                onDoubleClick = {  },
                onLongClick = {                     // storing the contact details in the state so that we can update it
                    viewModel.state.value.id.value = contact.id
                    viewModel.state.value.name.value = contact.name
                    viewModel.state.value.phoneNumber.value = contact.phoneNumber
                    viewModel.state.value.email.value = contact.email

                    navController.navigate(Routes.AddContactUI){}
                }
            )
            ,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text(text = contact.name, fontSize = 18.sp,fontFamily = FontFamily.Serif)
                Text(text = contact.phoneNumber,fontSize = 14.sp,fontFamily = FontFamily.Serif)
//                Text(text = contact.email,fontSize = 16.sp,fontFamily = FontFamily.Serif)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .clickable{  // before delete first copy the contact details to the state so that we can delete it
                        viewModel.state.value.id.value = contact.id
                        viewModel.state.value.name.value = contact.name
                        viewModel.state.value.phoneNumber.value = contact.phoneNumber
                        viewModel.state.value.email.value = contact.email
                        viewModel.deleteContact()
                    }
            )
        }
    }

}
