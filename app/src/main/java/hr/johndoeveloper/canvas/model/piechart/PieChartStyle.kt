package hr.johndoeveloper.canvas.model.piechart

import hr.johndoeveloper.canvas.constants.INDIVIDUAL_Slices
import hr.johndoeveloper.canvas.constants.SOLID

data class PieChartStyle(
    var chartOutlineThickness: Double = 0.0,
    var chartSpacing: Double = 0.0,
    var chartShadow: Double = 0.0,
    var animationStyle: String = INDIVIDUAL_Slices,
    var chartDesign: String = SOLID
)