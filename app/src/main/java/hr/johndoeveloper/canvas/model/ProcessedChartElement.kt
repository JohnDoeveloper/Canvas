package hr.johndoeveloper.canvas.model

data class ProcessedChartElement(
    val text: String,
    val quantity: Double,
    val percentage: Double,
    val arcStart: Float,
    var arcSweepAngle: Float,
    val finalArcSweepAngle: Float
)