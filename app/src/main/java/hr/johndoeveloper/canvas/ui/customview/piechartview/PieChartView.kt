package hr.johndoeveloper.canvas.ui.customview.piechartview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter

class PieChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var adapter: PieChartViewAdapter

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}