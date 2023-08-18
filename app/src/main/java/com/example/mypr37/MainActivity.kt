package com.example.mypr37

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypr37.ui.theme.MyPr37Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyPr37Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    G(applicationContext)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun G(applicationContext: Context) {
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var userViewModel: UserViewModel =
        viewModel(factory = MyViewModelFactory(applicationContext as Application))
    val openDialog = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        val textFieldValue = remember { mutableStateOf(TextFieldValue()) }

        TextField(value = textFieldValue.value, onValueChange = {
            textFieldValue.value = it
        })
        Button(onClick = {
            userViewModel.saveUser(User(name = textFieldValue.value.text, age = 10))

        }) {

        }
    }

    if (openDialog.value) {
        AlertDialogWithUser(user = selectedUser?:User(name="", age=0), onEdit = { user ->
            userViewModel.saveUser(user)
            openDialog.value = false
        }, onDelete = { user ->
            userViewModel.delete(user)
            openDialog.value = false
        })
    }


    userViewModel.getListDB()
    var state = userViewModel.stateFlow.collectAsState()
    Column() {


        LazyColumn() {
            items(state.value) {
                Text(it.name, modifier = Modifier.clickable {
//                    userViewModel.delete(it)
                    selectedUser=it
                    openDialog.value = true
                })
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogWithUser(
    user: User,
    onEdit: (User) -> Unit,
    onDelete: (User) -> Unit
) {
    var user:User by remember { mutableStateOf(user) }
    var editedName by remember { mutableStateOf(user.name) }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "${user.name}") },
        text = {
            Column {
                TextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    label = { Text(text = "Name") }
                )
//                TextField(
//                    value = user.age.toString(),
//                    onValueChange = { user.age = it.toInt()},
//                    label = { Text(text = "Age") }
//                )

            }
        },
        confirmButton = {
            Row {
                Button(
                    onClick = {
                        if (user != null) {
                            onEdit(user.copy(name=editedName))
                        }
                    }
                ) {
                    Text(text = "Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (user != null) {
                            onDelete(user)
                        }
                    }
                ) {
                    Text(text = "Delete", color = Color.Red)
                }
            }
        },
        dismissButton = {}
    )
}