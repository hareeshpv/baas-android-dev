package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.TransactionDetailsResponse
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class GetTransactionDetailApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(context, requestMap, ApiName.GET_TRANSACTION_DETAIL, sdkCallback) {

    override fun getRelativeUrl(): String = "transactions/details?id=" +
            "${requestMap[BaaSConstants.BS_KEY_ID]}"

//    override fun getRelativeUrl(): String =
//        "transactions?startDate=${requestMap[BaaSConstants.BS_KEY_START_DATE]}&endDate=${requestMap[BaaSConstants.BS_KEY_END_DATE]}&accountType=${requestMap[BaaSConstants.BS_KEY_ACCOUNT_TYPE]}&debitIndicator=${requestMap[BaaSConstants.BS_KEY_DEBIT_INDICATOR]}"


    override fun getRequestMethod(): RequestMethod = RequestMethod.GET
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.ACCESS_TOKEN
    override fun getRequestData(): String = ""
    override fun getResponseModel(): ApiResponse = TransactionDetailsResponse()
}