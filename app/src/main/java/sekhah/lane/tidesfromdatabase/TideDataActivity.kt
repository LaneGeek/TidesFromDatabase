package sekhah.lane.tidesfromdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tide_data.*

class TideDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tide_data)

        // We extract the data we need from MainActivity
        val day = intent.getIntExtra("Day", 0)
        val month = intent.getIntExtra("Month", 0)
        val year = intent.getIntExtra("Year", 0)
        val city = intent.getStringExtra("City")

        val db = TideOpenHelper(this, null)

        val dayFormat = if (day < 10)  "0${day}" else "${day}"
        val monthFormat = if (month < 9)  "0${month + 1}" else "${month + 1}"

        val tidesCursor = db.getTides("${year}/${monthFormat}/${dayFormat}")

        val tidesList = mutableListOf<TideDataEntry>()

        textView.text = "Tides for ${city} on ${month + 1}/${day}/${year}:-"

        tidesCursor?.moveToFirst()
        if (tidesCursor != null && tidesCursor.count != 0) {
            val tide = TideDataEntry()
            tide.city = tidesCursor.getString(1)
            tide.date = tidesCursor.getString(2)
            tide.day = tidesCursor.getString(3)
            tide.time = tidesCursor.getString(4)
            tide.predictionInCm = tidesCursor.getString(5)
            tide.highLow = tidesCursor.getString(6)
            tidesList.add(tide)
            tidesCursor.moveToNext()
        }




    }
}
