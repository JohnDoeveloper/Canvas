package hr.johndoeveloper.canvas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.model.ChartElement
import hr.johndoeveloper.canvas.model.piechart.PieChartStyle
import hr.johndoeveloper.canvas.ui.customview.piechartview.adapter.PieChartViewAdapter
import kotlinx.android.synthetic.main.fragment_pie_chart.*

class ElementListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_element_list, container, false)
    }

}