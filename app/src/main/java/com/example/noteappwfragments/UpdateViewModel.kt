package com.example.noteappwfragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel (application : Application) : AndroidViewModel(application) {

    val db = Firebase.firestore
    var TAG = "Main"

    fun updateNote(pk: String, note: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var updatedNote = hashMapOf(
                "Note" to note
            )
            db.collection("notes").document(pk)
                .set(updatedNote)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.d(TAG, "Error deleting document", e) }
        }
    }
}