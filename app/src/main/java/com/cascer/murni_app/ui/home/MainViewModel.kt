package com.cascer.murni_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cascer.murni_app.data.model.User
import com.cascer.murni_app.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun editUser(username: String, password: String, age: Int) = viewModelScope.launch {
        _isLoading.postValue(true)
        if (repository.edit(username, password, age)) {
            _isLoading.postValue(false)
            _isSuccess.postValue(true)
        } else {
            _isLoading.postValue(false)
            _isError.postValue(true)
        }
    }

    fun getUser(username: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        _user.postValue(repository.user(username))
        _isLoading.postValue(false)
    }
}