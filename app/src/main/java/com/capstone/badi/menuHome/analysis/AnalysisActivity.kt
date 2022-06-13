package com.capstone.badi.menuHome.analysis

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.badi.R
import com.capstone.badi.databinding.ActivityAnalysisBinding
import com.capstone.badi.model.Parser
import com.capstone.badi.model.SalesForcasting
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieDataSet

class AnalysisActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

       mainBinding.progressBar.visibility = View.VISIBLE

        val streamBananas = resources.openRawResource(R.raw.sales_forcasting)
        val bananaData = Parser.toDataSet(streamBananas.reader())

        val bananaDataSet = getEntriesFromCSV(R.raw.sales_forcasting, "Banana Bread")
        mainBinding.lineChart.data = LineData(
            bananaDataSet,
        )

        val meninggal = ArrayList<Entry>()
//        meninggal.add(Entry(0F, 12F))
//        meninggal.add(Entry(1F, 13F))
//        meninggal.add(Entry(2F, 11F))
//        meninggal.add(Entry(3F, 10F))
//        meninggal.add(Entry(4F, 7F))
//        meninggal.add(Entry(5F, 11F))
        meninggal.add(Entry(6F, 12F))
        meninggal.add(Entry(7F, 13F))
        meninggal.add(Entry(8F, 14F))
        meninggal.add(Entry(9F, 13F))

        val sembuh = ArrayList<Entry>()
        sembuh.add(Entry(0F, 9F))
        sembuh.add(Entry(1F, 11F))
        sembuh.add(Entry(2F, 10F))
        sembuh.add(Entry(3F, 11F))
        sembuh.add(Entry(4F, 11F))
        sembuh.add(Entry(5F, 13F))
//        sembuh.add(Entry(6F, 12F))
//        sembuh.add(Entry(7F, 18F))
//        sembuh.add(Entry(8F, 30F))
//        sembuh.add(Entry(9F, 30F))

        val forcastingLineDataSet = LineDataSet(meninggal, "Forcasting")
        forcastingLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        forcastingLineDataSet.color = Color.RED
        forcastingLineDataSet.circleRadius = 5f
        forcastingLineDataSet.setCircleColor(Color.RED)


        val kasusLineDataSet = LineDataSet(sembuh, "Item Sold")
        kasusLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        kasusLineDataSet.color = Color.BLUE
        kasusLineDataSet.circleRadius = 5f
        kasusLineDataSet.setCircleColor(Color.BLUE)


//Setup Legend
        val legend = mainBinding.lineChart.legend
        legend.isEnabled = true
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)


        mainBinding.lineChart.description.isEnabled = false
        mainBinding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        mainBinding.lineChart.data = LineData(kasusLineDataSet,forcastingLineDataSet)
        mainBinding.lineChart.animateXY(100, 500)

        var date = ArrayList<String>();
        date.add("01-Apr")
        date.add("02-Apr")
        date.add("03-Apr")
        date.add("04-Apr")
        date.add("05-Apr")
        date.add("06-Apr")
        date.add("07-Apr")
        date.add("08-Apr")
        date.add("09-Apr")
        date.add("10-Apr")
        date.add("11-Apr")
        date.add("12-Apr")
        date.add("13-Apr")
        date.add("14-Apr")
        date.add("15-Apr")

        val tanggal = AxisDateFormatter(date.toArray(arrayOfNulls<String>(date.size)))
        mainBinding.lineChart.xAxis?.setValueFormatter(tanggal);

        mainBinding.progressBar.visibility = View.GONE



    }

    private fun getEntriesFromCSV(rawResId: Int, label: String): LineDataSet {

        var data: List<SalesForcasting>? = null
        resources.openRawResource(rawResId).use { stream ->
            data = Parser.toDataSet(stream.reader())
        }
        val entries: MutableList<Entry> = ArrayList()
        data?.mapIndexed { index, foodSearch ->

        }

        return LineDataSet(entries, label)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}