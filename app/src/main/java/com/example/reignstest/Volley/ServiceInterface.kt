package com.example.reigndevtest.Volley

import org.json.JSONObject

interface ServiceInterface {
    fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}