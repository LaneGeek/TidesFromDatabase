package sekhah.lane.tidesfromdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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

        // Open the database
        val db = TideOpenHelper(this, null)

        // Make variables to be used for date format query
        val dayFormat = if (day < 10) "0${day}" else "${day}"
        val monthFormat = if (month < 9) "0${month + 1}" else "${month + 1}"

        // Make the query and create a heading text view
        val tidesCursor = db.getTides(city, "${year}/${monthFormat}/${dayFormat}")
        textView.text = "Tides for ${city} on ${month + 1}/${day}/${year}:-"

        // Extract the tide data from the cursor
        if (tidesCursor != null) {
            tidesCursor.moveToFirst()
            val output = Array(tidesCursor.count) { "" }
            output.forEachIndexed() { i, _ ->
                run {
                    val tide = TideData()
                    tide.city = tidesCursor.getString(1)
                    tide.date = tidesCursor.getString(2)
                    tide.day = tidesCursor.getString(3)
                    tide.time = tidesCursor.getString(4)
                    tide.predictionInCm = tidesCursor.getString(5)
                    tide.highLow = tidesCursor.getString(6)
                    output[i] = "${tide.city} ${tide.date} ${tide.day} ${tide.time} ${tide.predictionInCm} ${tide.highLow}"
                    tidesCursor.moveToNext()
                }
            }

            // Render the list view
            listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, output)
        } else {
            Toast.makeText(this, "No tides found", Toast.LENGTH_SHORT).show()
        }

    }
}
