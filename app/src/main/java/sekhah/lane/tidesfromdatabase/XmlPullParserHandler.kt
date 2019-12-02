// This XML parser was made with the help of an online tutorial on JavaPoint which
// you can find here https://www.javatpoint.com/kotlin-android-xmlpullparser-tutorial

package sekhah.lane.tidesfromdatabase

import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class XmlPullParserHandler {
    private val tidesData = ArrayList<TideData>()
    private var tideData: TideData? = null
    private var text: String? = null

    fun parse(inputStream: InputStream): List<TideData> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagName.equals("item", ignoreCase = true)) {
                        tideData = TideData()
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagName.equals("item", ignoreCase = true)) {
                        tideData?.let { tidesData.add(it) }
                    } else if (tagName.equals("date", ignoreCase = true)) {
                        tideData!!.date = text
                    } else if (tagName.equals("day", ignoreCase = true)) {
                        tideData!!.day = text
                    } else if (tagName.equals("time", ignoreCase = true)) {
                        tideData!!.time = text
                    } else if (tagName.equals("pred_in_cm", ignoreCase = true)) {
                        tideData!!.predictionInCm = text
                    } else if (tagName.equals("highlow", ignoreCase = true)) {
                        tideData!!.highLow = text
                    }
                    else -> {
                    }
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return tidesData
    }
}
