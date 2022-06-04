package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName

class SetLimitResponse : ApiResponse() {
    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("msg")
    var message: String? = null
}