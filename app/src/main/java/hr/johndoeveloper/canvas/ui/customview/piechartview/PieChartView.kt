package hr.johndoeveloper.canvas.ui.customview.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.application.CanvasApp
import hr.johndoeveloper.canvas.common.colourList
import hr.johndoeveloper.canvas.common.getListOfPaintObjects
import hr.johndoeveloper.canvas.constants.centralCutout
import hr.johndoeveloper.canvas.constants.solid
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter

class PieChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var adapter: PieChartViewAdapter
    var pieChartRect: RectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    var innerPieChartRect: RectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updatePieChartRect(w, h)
        updateInnerPieChartRect(w, h)
    }

    private fun updatePieChartRect(width: Int, height: Int) {
        val left = (width - height) / 2f
        val right = (width - height) / 2f + height.toFloat()
        pieChartRect = RectF(left, 0f, right, height.toFloat())
    }

    private fun updateInnerPieChartRect(width: Int, height: Int) {
        val padding = height / 1.4f
        val left = (width - height) / 2f
        val right = (width - height) / 2f + height.toFloat()
        innerPieChartRect = RectF(left + padding, 0f + padding, right - padding, height - padding)
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
            for (i in 0 until adapter.processedElementsList.size)
                canvas.drawArc(
                    pieChartRect,
                    adapter.processedElementsList[i].arcStart,
                    adapter.processedElementsList[i].arcSweepAngle,
                    true,
                    adapter.paintList[i]
                )
    }

    private fun drawPieCutCutout(canvas: Canvas) {
        if (this::adapter.isInitialized)
            for (i in 0 until adapter.processedElementsList.size) {
                canvas.drawArc(
                    pieChartRect,
                    adapter.processedElementsList[i].arcStart,
                    adapter.processedElementsList[i].arcSweepAngle,
                    true,
                    adapter.paintList[i]
                )
                canvas.drawArc(
                    innerPieChartRect,
                    0f,
                    360f,
                    true,
                    Paint().apply {
                        color = ContextCompat.getColor(
                            CanvasApp.getApplicationContext(),
                            R.color.colourWhite
                        )
                    }
                )
            }
    }
}