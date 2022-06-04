package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.RequestCreator
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.UpdateBeneficiaryResponse
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class UpdateBeneficiaryApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.UPDATE_BENEFICIARY, sdkCallback
) {
    val beneficiaryId: String = requestMap[BaaSConstants.BS_KEY_BENEFICIARY_ID].toString();
    override fun getRelativeUrl(): String =
        "user/${beneficiaryId}/beneficiary"

    override fun getRequestMethod(): RequestMethod = RequestMethod.PUT
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.ACCESS_TOKEN

    override fun getRequestData(): String =
        RequestCreator(
            getContentType(),
            requestMap.apply { remove(BaaSConstants.BS_KEY_BENEFICIARY_ID) }).createRequest()


    override fun getResponseModel(): ApiResponse = UpdateBeneficiaryResponse()
}