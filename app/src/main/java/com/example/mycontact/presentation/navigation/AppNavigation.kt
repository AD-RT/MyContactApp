package com.example.mycontact.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycontact.ContactViewModel
import com.example.mycontact.presentation.screens.AddContactUI
import com.example.mycontact.presentation.screens.HomeScreenUI
import com.example.mycontact.presentation.screens.profile


@Composable
fun AppNavigation(modifier: Modifier= Modifier, viewModel: ContactViewModel = hiltViewModel()){
    var navController = rememberNavController()
    val state = viewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
           HomeScreenUI(state = state.value, viewModel = viewModel, modifier = modifier, navController = navController)
        }
        composable<Routes.AddContactUI> {
            AddContactUI(state = state.value, OnEvent = { viewModel.insertContact() }, navController = navController)
        }
        composable<Routes.profile> {
            profile(state = state.value,viewModel = viewModel, navController = navController)
        }
    }
}