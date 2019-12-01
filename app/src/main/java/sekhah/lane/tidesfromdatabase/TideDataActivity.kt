package sekhah.lane.tidesfromdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tide_data.*

class TideDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tide_data)

        // We extract the data we need
        val day = intent.getIntExtra("Day", 0)
        val month = intent.getIntExtra("Month", 0)
        val year = intent.getIntExtra("Year", 0)
        val city = intent.getStringExtra("City")

        dayTV.text = day.toString()
        monthTV.text = month.toString()
        yearTV.text = year.toString()
        cityTV.text = city

    }
}
