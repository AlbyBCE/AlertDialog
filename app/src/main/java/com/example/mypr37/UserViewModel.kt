package com.example.mypr37

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(context: Context): ViewModel() {
    val stateFlow: StateFlow<List<User>> get() = mutableStateFlow
    private val mutableStateFlow :MutableStateFlow<List<User>> = MutableStateFlow(listOf())

    private var repository: UserRepo = UserRepo(context)

    fun getList(){
        viewModelScope.launch {
            repository.list().collect{
                mutableStateFlow.value=it
            }
        }
    }

    fun getListDB(){
        viewModelScope.launch {
            repository.listDB().collect{
                mutableStateFlow.value=it
            }
        }
    }
    fun saveUser(user:User){
        viewModelScope.launch {
            repository.saveDB(user)
        }
    }


    fun delete(user: User){
        viewModelScope.launch {
            repository.delete(user)
        }
    }

}
class MyViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
    }
}