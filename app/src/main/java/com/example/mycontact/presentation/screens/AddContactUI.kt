package com.example.mycontact.presentation.screens


import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.mycontact.AppState
import com.example.mycontact.R
import com.example.mycontact.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactUI(modifier: Modifier = Modifier,
                 viewModel: ContactViewModel = hiltViewModel(),
                 OnEvent: () -> Unit = {},
                 state : AppState = AppState(),  // we did not make the remember state according to the clean architecture principle so we defined the state in the AppState class
                 navController: NavHostController= rememberNavController()

                 ){
    val viewState by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.HomeScreen)}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "null")
                    }
                },
                title = {
                    Text(text = "Add Contact")
                }
            )
        },

    ){ innerPadding ->
        Column(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
//            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
//            Spacer(modifier=Modifier.height(16.dp))
//           // Display the selected image or a placeholder
//            val imageUri = viewState.selectedImageUri.value // Access the value from the state
//            Log.d("AddContactUI", "Current Image URI in UI: $imageUri")
//
//            val painter = if (imageUri != null) {
//                rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current)
//                        .data(imageUri)
//                        .crossfade(true)
//                        .build()
//                )
//            } else {
//                // Use a placeholder image when no image is selected
//                painterResource(id = R.drawable.u) // Replace with your default image resource
//            }
//                Image(
//                    painter = painter,
//                    contentDescription = null,
//                    modifier=modifier
//                        .size(180.dp)
//                        .clip(CircleShape)
//                        .clickable {// Launch the photo picker when the image is clicked
//                            pickMediaLauncher.launch(
//                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                            )   },
//                    contentScale = ContentScale.Crop
//                )
//


            DetailsContactUI(placeholder = "Name", valueChange = {state.name.value = it}, label = "Enter Name", value = state.name.value, contentDescription = "Person Icon", Id = R.drawable.baseline_person_24)
            Spacer(modifier=Modifier.height(16.dp))
            DetailsContactUI(placeholder = "Phone Number", valueChange = {state.phoneNumber.value = it}, label = "Enter Phone Number", value = state.phoneNumber.value, contentDescription = "Phone Icon", Id = R.drawable.baseline_add_call_24)
            Spacer(modifier=Modifier.height(16.dp))
            DetailsContactUI(placeholder = "Email", valueChange = {state.email.value = it}, label = "Enter Email", value =state.email.value, contentDescription = "Email Icon", Id = R.drawable.baseline_email_24)

            Spacer(modifier=Modifier.height(16.dp))
            Button(
                onClick = {
                    OnEvent.invoke()
                    navController.navigateUp()   // from this we can go back to the previous screen as entered the save button
                          },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}


@Composable
fun DetailsContactUI( placeholder: String, valueChange: (String) -> Unit, label: String, value: String, contentDescription: String,Id : Int ){
    OutlinedTextField(
        leadingIcon = { Icon(painter = painterResource(id = Id), contentDescription = contentDescription) },
        value = value,
        onValueChange = valueChange,
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        keyboardOptions = if (placeholder == "Phone Number") {
            KeyboardOptions(keyboardType = KeyboardType.Phone)
        } else if(placeholder == "Email") {
            KeyboardOptions(keyboardType = KeyboardType.Email)
        }else{
            KeyboardOptions(keyboardType = KeyboardType.Text)
        },
        singleLine = true
    )
}
