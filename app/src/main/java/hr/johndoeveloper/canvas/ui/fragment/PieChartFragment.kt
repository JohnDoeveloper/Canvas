package hr.johndoeveloper.canvas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.constants.fullCircle
import hr.johndoeveloper.canvas.constants.individualCuts
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.model.piechart.PieChartStyle
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter
import hr.johndoeveloper.canvas.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pie_chart.*

class PieChartFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pie_chart, container, false)
    }

    override fun onResume() {
        super.onResume()
        createAdapter()
    }

    fun createAdapter() {
        pieChartView.adapter = PieChartViewAdapter(
            listOf(
                ChartElement("this", 50.0),
                ChartElement("that", 80.0),
                ChartElement("that", 10.0),
                ChartElement("that", 60.0),
                ChartElement("that", 70.0)
            ),
            PieChartStyle(
                animationStyle = fullCircle
            )
        )
    }


}