package com.capstone.badi.menuHome.myNotes.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.badi.R
import com.capstone.badi.alarm.AlarmManagerActivity
import com.capstone.badi.databinding.ActivityMyNotesBinding
import com.capstone.badi.menuHome.myNotes.helper.ViewModelFactory1
import com.capstone.badi.menuHome.myNotes.ui.insert.NoteAddUpdateActivity

class MyNotesActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMyNotesBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMyNotesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        this.title = "MyNotes"

        val myNotesViewModel = obtainViewModel(this)
        myNotesViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        adapter = NoteAdapter()

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
        binding?.fabAlarm?.setOnClickListener { view ->
            if (view.id == R.id.fab_alarm) {
                val intent = Intent(this, AlarmManagerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MyNoteViewModel {
        val factory = ViewModelFactory1.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MyNoteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
