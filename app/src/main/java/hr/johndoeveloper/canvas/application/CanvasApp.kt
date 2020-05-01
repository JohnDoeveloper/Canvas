package hr.johndoeveloper.canvas.application

import android.app.Application
import android.content.Context

class CanvasApp: Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object{
        private lateinit var instance: CanvasApp
        fun getApplicationContext(): Context = instance.applicationContext
    }

}