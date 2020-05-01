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
import kotlin.math.sqrt

/** The adapter should through the use of Kotlin Coroutines animate the pie chart so that
 * either each section starts and ends growing at the same time or that the chart
 * grows in a 0 to 360 degree manner.
 *
 * There is a possibility of implementing both solutions and leaving the choice up
 * to the user via a constant passed as an argument to the adapter
 */


class PieChartViewAdapter(
    val listOfElements: List<ChartElement>,
    val pieChartStyle: PieChartStyle
) {

    var total = 0.0
    var processedElementsList = mutableListOf<ProcessedChartElement>()
    val paintList = getListOfPaintObjects(colourList)

    init {
        displayChart()
    }

    fun calculateTotal() {
        listOfElements.forEach {
            total += it.quantity
        }
    }

    fun processElements() {
        calculateTotal()
        for (i in listOfElements.indices) {
            var arcStart = when (i) {
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

    fun displayChart() {
        processElements()
        if (pieChartStyle.animationStyle == fullCircle) {
            GlobalScope.launch {
                var totalTime = 0f
                val delayTime = 1L
                var remainder = 0f
                processedElementsList.forEach {
                    val arcSweepAngle = it.finalArcSweepAngle
                    while (it.arcSweepAngle < arcSweepAngle) {
                        when {
                            it.arcSweepAngle < arcSweepAngle -> {
                                it.arcSweepAngle += ((360 / 360f) * sqrt(
                                    totalTime/100 + remainder
                                ))}
                                it.arcSweepAngle >= arcSweepAngle -> {
                                    remainder = it.arcSweepAngle - arcSweepAngle
                                    it.arcSweepAngle = arcSweepAngle
                                }
                            }
                            totalTime += delayTime
                                    delay (delayTime)
                        }
                    }
                }
            }
            if (pieChartStyle.animationStyle == individualCuts) {
                processedElementsList.forEach {
                    val arcSweepAngle = it.finalArcSweepAngle
                    var totalTime = 0f
                    val delayTime = 8L
                    GlobalScope.launch {
                        while (it.arcSweepAngle < arcSweepAngle) {
                            when {
                                it.arcSweepAngle < arcSweepAngle -> it.arcSweepAngle += ((it.finalArcSweepAngle / 400) * sqrt(
                                    totalTime
                                ))
                                it.arcSweepAngle > arcSweepAngle -> it.arcSweepAngle = arcSweepAngle
                            }
                            totalTime += delayTime
                            delay(delayTime)
                        }
                    }
                }
            }
        }
    }