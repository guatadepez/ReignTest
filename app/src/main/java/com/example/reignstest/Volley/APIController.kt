package com.example.reigndevtest.Volley

import org.json.JSONObject

/**
 *
 * Created by: GuatadepeZ
 * APIController: Used to control the calls that we use with Volley.
 *
 */
class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.get(path, params, completionHandler)
    }
}