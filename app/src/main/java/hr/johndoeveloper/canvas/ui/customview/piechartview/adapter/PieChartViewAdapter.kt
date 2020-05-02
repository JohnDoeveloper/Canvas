package hr.johndoeveloper.canvas.ui.customview.piechartview.adapter

import hr.johndoeveloper.canvas.common.colourList
import hr.johndoeveloper.canvas.common.getListOfPaintObjects
import hr.johndoeveloper.canvas.constants.fullCircle
import hr.johndoeveloper.canvas.constants.individualCuts
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.model.ProcessedChartElement
import hr.johndoeveloper.canvas.model.piechart.PieChartStyle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PieChartViewAdapter(
    private val listOfElements: List<ChartElement>,
    val pieChartStyle: PieChartStyle
) {

    var total = 0.0
    var processedElementsList = mutableListOf<ProcessedChartElement>()
    val paintList = getListOfPaintObjects(colourList)

    init {
        displayChart()
    }

    private fun calculateTotal() {
        listOfElements.forEach {
            total += it.quantity
        }
    }

    private fun processElements() {
        calculateTotal()
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

    private fun getSlowdownValue(constant: Float, totalTime: Long, remainder: Float = 0f) =
        constant / totalTime + remainder

    private fun displayChart() {
        processElements()
        when (pieChartStyle.animationStyle) {
            fullCircle -> animateFullCircle()
            individualCuts -> animateIndividualCutouts()
        }
    }

    private fun animateFullCircle() {
        GlobalScope.launch {
            var totalTime = 25L
            val delayTime = 1L
            var remainder = 0f
            processedElementsList.forEach {
                val arcSweepAngle = it.finalArcSweepAngle
                while (it.arcSweepAngle < arcSweepAngle) {
                    val newSweepAngle = getSlowdownValue(200f, totalTime, remainder)
                    when {
                        newSweepAngle < arcSweepAngle -> it.arcSweepAngle += newSweepAngle
                        newSweepAngle >= arcSweepAngle -> {
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

    private fun animateIndividualCutouts() {
        processedElementsList.forEach {
            val arcSweepAngle = it.finalArcSweepAngle
            var totalTime = 10L
            val delayTime = 8L
            GlobalScope.launch {
                while (it.arcSweepAngle < arcSweepAngle) {
                    val newSweepAngle = getSlowdownValue(it.finalArcSweepAngle * 2, totalTime)
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
}
