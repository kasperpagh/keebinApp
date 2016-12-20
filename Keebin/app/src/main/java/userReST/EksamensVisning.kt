package userReST

import android.os.AsyncTask
import kasper.pagh.keebin.DatabaseHandler
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun AsyncTask<String, Void, String>.makeGetCall(url: URL, databaseH: DatabaseHandler): String{
    var input: InputStream? = null
    var bufferedReader: BufferedReader? = null
    var sb: StringBuilder? = null

    try {
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.readTimeout = 10000
        connection.connectTimeout = 15000
        connection.doInput = true
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("accessToken", databaseH.getTokenByName("accessToken").tokenData)
        connection.setRequestProperty("refreshToken", databaseH.getTokenByName("refreshToken").tokenData)

        connection.connect()


        input = connection.inputStream
        val code = "" + connection.responseCode



        if (code.equals("200", ignoreCase = true))
            run {
                val accessToken = connection.getHeaderField("accessToken")
                val refreshToken = connection.getHeaderField("refreshToken")

                if (databaseH.getTokenByName("refreshToken").tokenData != refreshToken) {
                    databaseH.updateToken("refreshToken", refreshToken)
                }
                if (databaseH.getTokenByName("accessToken").tokenData != accessToken) {
                    databaseH.updateToken("accessToken", accessToken)
                }

                if (databaseH.getTokenByName("accessToken").tokenData != accessToken) {
                    databaseH.updateToken("accessToken", accessToken)
                }

            }

        bufferedReader = BufferedReader(InputStreamReader(input!!))
        sb = StringBuilder()
        var line: List<String>
        line = bufferedReader.readLines()
        var tempNumb = 0
        println("her er readlines " + line)
        while(tempNumb < line.size)  {
            sb.append(line[tempNumb] + "\n")
            println("her er sb:   "+ sb)
            tempNumb += 1
            println("lige efter vi sætter lortet op")

        }
        println("lige før vi går ind og closer")
        bufferedReader.close()
        println(" lige efter den snyder " + sb.toString())
        return sb.toString()
    } finally {
        if (input != null) {
            input.close()
        }
    }
}