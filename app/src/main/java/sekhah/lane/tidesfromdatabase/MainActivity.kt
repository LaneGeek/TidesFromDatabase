package sekhah.lane.tidesfromdatabase

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Our locations - easy to add more
        val cities = arrayOf("Astoria", "Florence", "Newport")

        // Open the database
        val db = TideOpenHelper(this, null)

        // If database empty fill it with data from XML files
        if (db.isEmpty()) {
            cities.forEach { city ->
                run {
                    val parser = XmlPullParserHandler()
                    val inputStream = assets.open("${city}.xml")
                    val tidesData = parser.parse(inputStream)
                    tidesData.forEach { x ->
                        run {
                            val tide = TideData()
                            tide.city = city
                            tide.date = x.date
                            tide.day = x.day
                            tide.time = x.time
                            tide.predictionInCm = x.predictionInCm
                            tide.highLow = x.highLow
                            db.addTide(tide)
                        }
                    }
                }
            }
        }

        // We name our radio buttons with cities
        radioButton1.text = cities[0]
        radioButton2.text = cities[1]
        radioButton3.text = cities[2]

        // We set our default date
        var day = 1
        var month = 0
        var year = 2019

        // Use the DatePicker to get the date from the user
        dateTextView.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                dateTextView.text = "Date: ${m + 1}/${d}/${y}"
                day = d
                month = m
                year = y
            }, 2019, 0, 1)
            datePicker.show()
        }

        // Start the second activity ans send the user selected data to it
        getTideDateButton.setOnClickListener {
            val city = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if (city != null && year != 0) {
                val intent = Intent(this, TideDataActivity::class.java)
                intent.putExtra("Day", day)
                intent.putExtra("Month", month)
                intent.putExtra("Year", year)
                intent.putExtra("City", city.text)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Choose a location", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
