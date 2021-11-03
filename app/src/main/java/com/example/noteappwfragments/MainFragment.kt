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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class MainFragment : Fragment() {

    val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }

    lateinit var eInput : EditText
    lateinit var bAdd : Button
    lateinit var rvItems : RecyclerView
    lateinit var rvAdapter : RVAdapter

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)


        eInput =  view.findViewById(R.id.eInput)
        bAdd = view.findViewById(R.id.bAdd)
        rvItems = view.findViewById(R.id.rvItems)

        myViewModel.getNotes().observe(viewLifecycleOwner, {
                notes -> rvAdapter.update(notes)
        })


        bAdd.setOnClickListener {
            var note = eInput.text.toString()
            myViewModel.addNote(note)
            eInput.text.clear()
        }

        rvAdapter = RVAdapter(this)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        // We call the 'getData' function from our ViewModel after a one second delay because Firestore takes some time
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            myViewModel.getNotes()
        }
    }

    fun updateNote(note : Notes){
        val bundle = Bundle()
        bundle.putString("pk", note.pk)
        bundle.putString("note", note.note)
        val fragment = SecondFragment()
        fragment.arguments = bundle
        Log.d("data from 1st fragment", "id $id , note $note")
        fragmentManager?.beginTransaction()?.replace(R.id.nav_container,fragment)?.commit()

//       Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_secondFragment)
    }
}

