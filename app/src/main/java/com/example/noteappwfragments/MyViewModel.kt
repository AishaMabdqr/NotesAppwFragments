package com.example.noteappwfragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel (application : Application) : AndroidViewModel(application) {

    private var notes: MutableLiveData<List<Notes>> = MutableLiveData()
    val db = Firebase.firestore
    var TAG = "Main"

    fun getNotes(): LiveData<List<Notes>> {
        return notes
    }

    fun addNote(notes: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val item = hashMapOf(
                "Note" to notes
            )
            db.collection("notes")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
            retrieveNotes()
        }
    }

    fun retrieveNotes() {

        var notesArray = ArrayList<Notes>()

        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                var details = "\n"

                for (document in result) {
                    document.data.map { (key, value) ->
                        notesArray.add(Notes(document.id, "$value"))
                    }
                }
                Log.d(TAG, "Notes Array = $notesArray")
                notes.postValue(notesArray)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    fun deleteNote(pk: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes").document(pk)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.d(TAG, "Error deleting document", e) }
            retrieveNotes()
        }
    }
}