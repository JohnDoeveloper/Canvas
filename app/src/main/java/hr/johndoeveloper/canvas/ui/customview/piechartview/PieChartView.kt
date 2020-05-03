package hr.johndoeveloper.canvas.ui.customview.piechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.application.CanvasApp
import hr.johndoeveloper.canvas.constants.CENTRAL_CUTOUT
import hr.johndoeveloper.canvas.constants.SOLID
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter
import kotlin.math.max
import kotlin.math.min

class PieChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    lateinit var adapter: PieChartViewAdapter
    var pieChartRect: RectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    var innerPieChartRect: RectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updatePieChartRect(w, h)
        updateInnerPieChartRect(w, h)
    }

    /**Refactor the first two functions below so that they don't violate DRY*/
    private fun updatePieChartRect(width: Int, height: Int) {
        val horizontalCenter = width / 2f
        val verticalCenter = height / 2f
        val min = min(horizontalCenter, verticalCenter)
        val left = horizontalCenter - min
        val right = horizontalCenter + min
        val top = horizontalCenter - min
        val bottom = horizontalCenter + min
        pieChartRect = RectF(left, top, right, bottom)
    }

    private fun updateInnerPieChartRect(width: Int, height: Int) {
        val padding = min(width,height) / 1.4f
        val horizontalCenter = width / 2f
        val verticalCenter = height / 2f
        val min = min(horizontalCenter, verticalCenter)
        val left = horizontalCenter - min + padding
        val right = horizontalCenter + min - padding
        val top = horizontalCenter - min + padding
        val bottom = horizontalCenter + min - padding
        innerPieChartRect = RectF(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (this::adapter.isInitialized)
            canvas?.let {
                when (adapter.pieChartStyle.chartDesign) {
                    SOLID -> drawPieCutSolid(it)
                    CENTRAL_CUTOUT -> drawPieCutCutout(it)
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