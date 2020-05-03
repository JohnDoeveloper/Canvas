package hr.johndoeveloper.canvas.ui.customview.piechartview.adapter

import hr.johndoeveloper.canvas.common.colourList
import hr.johndoeveloper.canvas.common.getListOfPaintObjects
import hr.johndoeveloper.canvas.constants.FULL_CIRCLE
import hr.johndoeveloper.canvas.constants.INDIVIDUAL_Slices
import hr.johndoeveloper.canvas.constants.NO_ANIMATION
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.model.ProcessedChartElement
import hr.johndoeveloper.canvas.model.piechart.PieChartStyle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

class PieChartViewAdapter(
    private val listOfElements: List<ChartElement>,
    val pieChartStyle: PieChartStyle
) {

    var total = 0.0
    var processedElementsList = mutableListOf<ProcessedChartElement>()
    val paintList = getListOfPaintObjects(colourList)

    fun drawChart() {
        calculateTotal()
        processElements()
        displayChart()
    }

    private fun calculateTotal() {
        total = 0.0
        listOfElements.forEach {
            total += it.quantity
        }
    }

    private fun processElements() {
        processedElementsList.clear()
        for (i in listOfElements.indices) {
            val arcStart = when (i) {
                0 -> 0f
                else -> processedElementsList[i - 1].arcStart + processedElementsList[i - 1].finalArcSweepAngle
            }
            processedElementsList.add(
                ProcessedChartElement(
                    text = listOfElements[i].text,
                    quantity = listOfElements[i].quantity,
                    percentage = listOfElements[i].quantity / total,
                    arcStart = arcStart,
                    arcSweepAngle = 0f,
                    finalArcSweepAngle = (360f * listOfElements[i].quantity / total).toFloat()
                )
            )
        }
    }

    /**Break down animation speed to slow, medium and fast and make it function with string constants
     *  instead of dilly dallying with number and corresponding mathematical function outputs*
     *
     *  or you know what, implement a time duration value, even better!*/
    private fun getSlowdownValue(constant: Float, totalTime: Float, remainder: Float = 0f) =
        constant / totalTime.pow(1/3) + remainder

    private fun displayChart() {
        when (pieChartStyle.animationStyle) {
            NO_ANIMATION -> noAnimation()
            FULL_CIRCLE -> animateFullCircle()
            INDIVIDUAL_Slices -> animateIndividualSlices()
        }
    }

    private fun animateFullCircle() {
        GlobalScope.launch {
            var totalTime = 2f
            val delayTime = 8L
            var remainder = 0f
            processedElementsList.forEach {
                val arcSweepAngle = it.finalArcSweepAngle
                var newSweepAngle = 0f
                while (it.arcSweepAngle < arcSweepAngle) {
                    newSweepAngle += getSlowdownValue(8.5f, totalTime, remainder)
                    when {
                        newSweepAngle < arcSweepAngle -> {
                            it.arcSweepAngle = newSweepAngle
                            remainder = 0f
                        }
                        else -> {
                            remainder = newSweepAngle - arcSweepAngle
                            it.arcSweepAngle = arcSweepAngle
                        }
                    }
                    totalTime += delayTime
                    delay(delayTime)
                }
            }
        }
    }

    private fun animateIndividualSlices() {
        processedElementsList.forEach {
            val arcSweepAngle = it.finalArcSweepAngle
            var totalTime = 14f
            val delayTime = 8L
            GlobalScope.launch {
                while (it.arcSweepAngle < arcSweepAngle) {
                    val newSweepAngle = getSlowdownValue(it.finalArcSweepAngle * 1/38f, totalTime)
                    when {
                        newSweepAngle < arcSweepAngle -> it.arcSweepAngle += newSweepAngle
                        newSweepAngle >= arcSweepAngle -> it.arcSweepAngle = arcSweepAngle
                    }
                    totalTime += delayTime
                    delay(delayTime)
                }
            }
        }
    }

    private fun noAnimation() {
        processedElementsList.forEach {
            it.arcSweepAngle = it.finalArcSweepAngle
        }
    }
}
