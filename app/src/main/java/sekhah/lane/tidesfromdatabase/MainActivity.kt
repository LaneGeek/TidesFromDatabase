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

        val cities = arrayOf("Astoria", "Florence", "Newport")
        val db = TideOpenHelper(this, null)

        if (db.isEmpty()) {
            // We parse each city in the array one at a time
            cities.forEach { city ->
                run {
                    // We use the XML parser
                    val parser = XmlPullParserHandler()
                    val inputStream = assets.open("${city}.xml")
                    val tidesData = parser.parse(inputStream)

                    // We fill the database from the parsed XML file
                    tidesData.forEach { x ->
                        run {
                            val tide = TideDataEntry()
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

        radioButton1.text = cities[0]
        radioButton2.text = cities[1]
        radioButton3.text = cities[2]

        var day = 0
        var month = 0
        var year = 0

        dateTextView.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                dateTextView.text = "Date: ${m + 1}/${d}/${y}"
                day = d
                month = m
                year = y
            }, 2019, 0, 1)
            datePicker.show()
        }

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
                Toast.makeText(this, "Enter the information above", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
