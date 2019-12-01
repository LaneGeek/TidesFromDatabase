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

        radioButton1.text = "Astoria"
        radioButton2.text = "Florence"
        radioButton3.text = "Newport"

        var day = 0
        var month = 0
        var year = 0

        dateButton.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                dateTextView.text = "Your chosen date: ${m + 1} / ${d} / ${y}"
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
            } else
                Toast.makeText(this, "Enter the information above", Toast.LENGTH_SHORT).show()
        }
    }
}
