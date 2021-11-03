package com.example.noteappwfragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SecondFragment : Fragment() {

    val updateViewModel by lazy { ViewModelProvider(this).get(UpdateViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second, container, false)

        val args = this.arguments
        val id = args?.getString("pk")
        val note = args?.get("note")

        Log.d("My data", "id $id , note $note")

        val bUpdate = view.findViewById<Button>(R.id.bUpdate)
        val eUpdate = view.findViewById<EditText>(R.id.eUpdate)

        bUpdate.setOnClickListener {
            var updatedNote = eUpdate.text.toString()
            updateViewModel.updateNote(id!!,updatedNote)
            eUpdate.text.clear()
            val fragment = MainFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_container,fragment)?.commit()
           // Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_mainFragment)
        }
        return view
    }

}