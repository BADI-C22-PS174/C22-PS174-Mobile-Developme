package com.capstone.badi.menuHome.myNotes.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.badi.menuHome.myNotes.ui.insert.NoteAddUpdateViewModel
import com.capstone.badi.menuHome.myNotes.ui.main.MyNoteViewModel

class ViewModelFactory1 private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory1? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory1 {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory1::class.java) {
                    INSTANCE = ViewModelFactory1(application)
                }
            }
            return INSTANCE as ViewModelFactory1
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyNoteViewModel::class.java)) {
            return MyNoteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}