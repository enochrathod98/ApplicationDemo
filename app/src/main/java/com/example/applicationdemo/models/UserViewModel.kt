package com.example.applicationdemo.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationdemo.models.UsersItem
import com.example.applicationdemo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {
    private var userLiveData = MutableStateFlow(emptyList<UsersItem>())
    val _userLiveData: MutableStateFlow<List<UsersItem>> get() = userLiveData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val list = repository.getUsers().users
            userLiveData.value = list

        }
    }
}