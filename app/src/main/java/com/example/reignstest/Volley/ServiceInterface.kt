package com.example.reigndevtest.Volley

import org.json.JSONObject


/**
 *
 * Created by: GuatadepeZ
 * ServiceInterface: Volley ServiceInterface
 *
 */

interface ServiceInterface {
    fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}