package com.example.mycontact

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontact.data.Repository
import com.example.mycontact.data.entity.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private var _state = MutableStateFlow<AppState>(AppState())
    private val _sortOrder = MutableStateFlow(SortOrder.ASCENDING)
    val AllContacts = repository.getAllContacts().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList<Contact>()
    )
    val state = combine(_state, AllContacts, _sortOrder) {
        state, contacts, sortOrder ->
        val sortedContacts = when (sortOrder) {
            SortOrder.ASCENDING -> contacts.sortedBy { it.name }
            SortOrder.DESCENDING -> contacts.sortedByDescending { it.name }
        }
        state.copy(AllContacts = sortedContacts)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // delay of 5 seconds after the last emission of the flow will stop collecting the flow and release the resources allocated to it
        initialValue = AppState()
    )
    // Function to toggle the sorting order
    fun toggleSortOrder(newSortOrder : SortOrder) {
        _sortOrder.value = if (_sortOrder.value == newSortOrder) {
            newSortOrder
        } else {
            newSortOrder
        }
    }
    fun insertContact(){
        val contact = Contact(
            id = state.value.id.value,

            name = state.value.name.value,
            phoneNumber = state.value.phoneNumber.value,
            email = state.value.email.value,


        )
        viewModelScope.launch {                        // as this function is running in background thread so we have to use viewModelScope
            repository.UpsertContact(contact)
        }
        // resetting the blank values so that after saved the contact if you again press add contact button you get empty spaces

            state.value.name.value = ""
            state.value.phoneNumber.value = ""
            state.value.email.value = ""
            state.value.id.value = 0
    }
    fun deleteContact(){
        val contact = Contact(
            id = state.value.id.value,

            name = state.value.name.value,
            phoneNumber = state.value.phoneNumber.value,
            email = state.value.email.value
        )
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
        state.value.name.value = ""
        state.value.phoneNumber.value = ""
        state.value.email.value = ""
        state.value.id.value = 0
    }

}
// it should be data class so that we get access to function like copy etc
data class AppState(                                       // this class is used to store the state of the app
    var loading : Boolean = false,
    var AllContacts : List<Contact> = emptyList(),
    var error : String = "",
    val id : MutableState<Int> = mutableStateOf(0),
    var name : MutableState<String> = mutableStateOf(""),
    var phoneNumber : MutableState<String> = mutableStateOf(""),
    var email : MutableState<String> = mutableStateOf("")

)

enum class SortOrder{
    ASCENDING,
    DESCENDING
}