package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.model.LimitConfig


class GetLimitsResponse : ApiResponse() {
    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("transactionLimits")
    var limitConfigs: LimitConfig? = null
//    var limitConfigs: List<LimitConfig>? = null

}