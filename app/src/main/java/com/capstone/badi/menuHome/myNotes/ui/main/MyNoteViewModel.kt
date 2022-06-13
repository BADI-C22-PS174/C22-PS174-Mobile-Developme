package com.capstone.badi.menuHome.myNotes.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.badi.menuHome.myNotes.database.Note
import com.capstone.badi.menuHome.myNotes.repository.NoteRepository

class MyNoteViewModel (application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}