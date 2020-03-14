package com.example.reigndevtest.Utils

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException

/**
 *
 * Created by: GuatadepeZ
 * Utils: Class to manage some Utils fun in application *
 * */

/**
 *
 * checkInternetConnection : fun to check the state of internet connection.
 *
 * */
fun checkInternetConnection(context: Context): Boolean {
    val connMgr = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeInfo = connMgr.activeNetworkInfo
    return activeInfo != null && activeInfo.isConnected
}
