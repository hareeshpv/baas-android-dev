package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

class ValidateCardKitModel() : ApiResponse() {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("time")
    var time: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("location")
    var location: String? = null
}