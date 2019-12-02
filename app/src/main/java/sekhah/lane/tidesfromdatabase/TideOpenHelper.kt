package sekhah.lane.tidesfromdatabase

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.database.Cursor

class TideOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, "TidesDB.db", factory, 1) {
    override fun onCreate(db: SQLiteDatabase) = db.execSQL(
        "CREATE TABLE Tides (\n" +
                " TideId INTEGER PRIMARY KEY,\n" +
                " City TEXT,\n" +
                " Date TEXT,\n" +
                " Day TEXT,\n" +
                " Time TEXT,\n" +
                " PredictionInCm TEXT,\n" +
                " HighLow TEXT\n" +
                ")"
    )

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Tides")
        onCreate(db)
    }

    // This functions adds a record to the database
    fun addTide(tideDataEntry: TideDataEntry) {
        val values = ContentValues()
        values.put("City", tideDataEntry.city)
        values.put("Date", tideDataEntry.date)
        values.put("Day", tideDataEntry.day)
        values.put("Time", tideDataEntry.time)
        values.put("PredictionInCm", tideDataEntry.predictionInCm)
        values.put("HighLow", tideDataEntry.highLow)
        //val db = this.writableDatabase
        this.writableDatabase.insert("Tides", null, values)
        this.writableDatabase.close()
    }

    // This function will get the first record and see if it exists
    fun isEmpty(): Boolean = this.readableDatabase.rawQuery("SELECT 1 FROM Tides LIMIT 1", null).count == 0

    // This function will return a cursor with tides for a particular date
    fun getTides(date: String): Cursor? = this.readableDatabase.rawQuery("SELECT * FROM Tides WHERE Date='${date}'", null)
}
