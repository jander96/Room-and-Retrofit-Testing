package com.example.roomtesting

import android.app.Application
import com.example.roomtesting.room.ItemDb

class App: Application() {
lateinit var db : ItemDb
    override fun onCreate() {
        super.onCreate()

    }
}