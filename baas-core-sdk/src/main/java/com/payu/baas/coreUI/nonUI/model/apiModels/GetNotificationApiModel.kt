package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.NotificationResponse
import com.payu.baas.coreUI.nonUI.network.NetworkHeader
import com.payu.baas.coreUI.nonUI.storage.SessionManager
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class GetNotificationApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.GET_NOTIFICATIONS, sdkCallback
) {
    override fun getRelativeUrl(): String = "benefits/notifications"
    override fun getRequestMethod(): RequestMethod = RequestMethod.GET
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.DEVICE_BINDING_ID
    override fun getResponseModel(): ApiResponse = NotificationResponse()
    override fun getAdditionalHeader(): NetworkHeader {
        return NetworkHeader().apply {
            SessionManager.getInstance(context).accessToken?.let {
                put(BaaSConstants.BS_KEY_ACCESS_TOKEN, it)
            }
        }
    }


}