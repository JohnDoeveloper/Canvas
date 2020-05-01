package hr.johndoeveloper.canvas.ui.customview.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.common.colourList
import hr.johndoeveloper.canvas.common.getListOfPaintObjects
import hr.johndoeveloper.canvas.constants.centralCutout
import hr.johndoeveloper.canvas.constants.solid
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter

class PieChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var adapter: PieChartViewAdapter
    var pieChartRect: RectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pieChartRect = RectF(0f, 0f, height.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (this::adapter.isInitialized)
            canvas?.let {
                when (adapter.pieChartStyle.chartDesign) {
                    solid -> drawPieCutSolid(it)
                    centralCutout -> drawPieCutCutout(it)
                }
                invalidate()
            }
    }

    private fun drawPieCutSolid(canvas: Canvas) {
        if (this::adapter.isInitialized)
            for( i in 0 until adapter.processedElementsList.size)
                canvas.drawArc(
                    pieChartRect,
                    adapter.processedElementsList[i].arcStart,
                    adapter.processedElementsList[i].arcSweepAngle,
                    true,
                    adapter.paintList[i]
                )
            }


    private fun drawPieCutCutout(canvas: Canvas) {}
}