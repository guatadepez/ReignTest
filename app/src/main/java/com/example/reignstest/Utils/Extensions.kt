package com.example.reigndevtest.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * Created by: GuatadepeZ
 * Extensions Utilities
 *
 */


/**
 *
 * ViewGroup.inflate: fun used to inflate the card view from the Adapter.
 *
 * */

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


