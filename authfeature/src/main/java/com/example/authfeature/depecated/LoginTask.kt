package com.example.authfeature.depecated

import android.os.AsyncTask
import org.json.JSONObject
import android.content.ContentValues
import android.content.Context
import android.util.Log
import org.json.JSONException
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class LoginTask(private val context: Context,private val success:(String)->Unit) : AsyncTask<String?, Void?, String?>() {
    private var username: String? = null
    private var password: String? = null
    override fun onPreExecute() {}


    override fun doInBackground(vararg params: String?): String? {
        val result: String
        var inputLine: String?

        //Set username and password
        username = params[0]
        password = params[1]
        try {
            //Setup URL connection
            val url = URL("https://assessment-sn12.halan.io/auth")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.addRequestProperty("Content-Type", "application/json")
            connection.doInput = true
            connection.doOutput = true
            val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
            val body = JSONObject()
            body.put("username", username)
            body.put("password", password)
            writer.write(body.toString())
            writer.close()
            connection.connect()
            val status = connection.responseCode
            val streamReader = InputStreamReader(connection.inputStream)
            val reader = BufferedReader(streamReader)
            val stringBuilder = StringBuilder()
            while (reader.readLine().also { inputLine = it } != null) {
                stringBuilder.append(inputLine)
            }
            reader.close()
            streamReader.close()
            result = stringBuilder.toString()
            return when (status) {
                200 -> result
                else -> null
            }
        } catch (e: MalformedURLException) {
            Log.w(ContentValues.TAG, "Exception while constructing URL" + e.message)
        } catch (e: IOException) {
            Log.w(ContentValues.TAG, "Exception occured while logging in: " + e.message)
        } catch (e: JSONException) {
            Log.w(ContentValues.TAG, "Exception occured while logging in: " + e.message)
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        if (result != null) {
            success(result)
//            Intent myIntent = new Intent(context, ProductsListActivity.class);
//            myIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            myIntent.putExtra("RESPONSE", result);
//            context.startActivity(myIntent);
        }
    }

}