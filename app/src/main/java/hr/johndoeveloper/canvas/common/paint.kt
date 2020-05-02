package hr.johndoeveloper.canvas.common

import android.graphics.Paint
import androidx.core.content.ContextCompat
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.application.CanvasApp

fun getPaintObject(colorResource: Int) = Paint().apply {
    color = ContextCompat.getColor(CanvasApp.getApplicationContext(), colorResource)
    flags = Paint.ANTI_ALIAS_FLAG
}

fun getListOfPaintObjects(listColourResources: List<Int>): List<Paint> {
    val mutableList: MutableList<Paint> = mutableListOf()
    listColourResources.forEach {
        mutableList.add(getPaintObject(it))
    }
    return mutableList
}

