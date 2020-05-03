package hr.johndoeveloper.canvas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.constants.*
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

    private fun setListeners() {
      setButtonListener()
        setChartDesignRadioGroupListener()
        setAnimationStyleRadioGroupListener()
    }

    private fun setButtonListener(){
        buttonDraw.setOnClickListener { pieChartView.adapter.drawChart() }
    }

    private fun setChartDesignRadioGroupListener(){
        radioGroupChartDesign.setOnCheckedChangeListener { group, checkedId ->
            pieChartView.adapter.pieChartStyle.chartDesign = when (checkedId) {
                R.id.radioButtonSolid -> SOLID
                R.id.radioButtonCutout -> CENTRAL_CUTOUT
                else -> SOLID
            }
        }
    }

    private fun setAnimationStyleRadioGroupListener(){
        radioGroupAnimationStyle.setOnCheckedChangeListener { group, checkedId ->
            pieChartView.adapter.pieChartStyle.animationStyle = when (checkedId) {
                R.id.radioButtonFullCircle -> FULL_CIRCLE
                R.id.radioButtonIndividualSlices -> INDIVIDUAL_Slices
                else -> NO_ANIMATION
            }
        }
    }

    private fun createAdapter() {
        pieChartView.adapter = PieChartViewAdapter(
            listOf(
                ChartElement("this", 50.0),
                ChartElement("that", 80.0),
                ChartElement("that", 10.0),
                ChartElement("that", 60.0),
                ChartElement("that", 70.0),
                ChartElement("that", 40.0),
                ChartElement("that", 35.0),
                ChartElement("that", 40.0),
                ChartElement("that", 40.0),
                ChartElement("that", 40.0)
            ),
            PieChartStyle(
                animationStyle = NO_ANIMATION,
                chartDesign = CENTRAL_CUTOUT
            )
        )
        setListeners()
    }


}