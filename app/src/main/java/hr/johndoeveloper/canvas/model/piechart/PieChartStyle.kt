package hr.johndoeveloper.canvas.model.piechart

import hr.johndoeveloper.canvas.constants.individualCuts
import hr.johndoeveloper.canvas.constants.solid

data class PieChartStyle(
    val chartOutlineThickness: Double = 0.0,
    val chartSpacing: Double = 0.0,
    val chartShadow: Double = 0.0,
    val animationStyle: String = individualCuts,
    val chartDesign: String = solid
)