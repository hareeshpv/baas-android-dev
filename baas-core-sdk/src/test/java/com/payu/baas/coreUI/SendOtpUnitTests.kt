package com.payu.baas.coreUI

import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import okhttp3.mockwebserver.RecordedRequest
import org.robolectric.annotation.Config
import retrofit2.Call
import java.util.concurrent.TimeUnit

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SendOtpUnitTests {
//    private var activity: MainActivity? = null
    var mockApiCaller: MockApiCallManger? = null

    @Before
    fun setUp() {
//        activity = Robolectric.buildActivity(MainActivity::class.java)
//            .create()
//            .resume()
//            .get()
        mockApiCaller = MockApiCallManger()
        mockApiCaller!!.initialize()
    }

//    @Test
//    @Throws(Exception::class)
//    fun checkActivityNotNull() {
//        TestCase.assertNotNull(activity)
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun checkValidMobileNumber() {
//        val etMobile = activity!!.findViewById<View>(R.id.etMobile) as EditText
//        TestCase.assertNotNull("Mobile number input field not found", etMobile)
//        TestCase.assertTrue(
//            "Phone number format is not correct.",
//            isValidNumber("8544941629")
//        )
//    }
//
//    fun isValidNumber(number: String?): Boolean {
//        return PhoneNumberUtils.isGlobalPhoneNumber(number)
//    }
//
//    /*
//    OTP must be 4-digit numeric
//     */
//    @Test
//    @Throws(Exception::class)
//    fun checkValidOTP() {
//        val etOTP = activity!!.findViewById<View>(R.id.etOTP) as EditText
//        TestCase.assertNotNull("OTP input field not found", etOTP)
//        TestCase.assertTrue(
//            "Incorrect OTP format.",
//            isValidOTP("2345")
//        )
//    }
//
//    fun isValidOTP(number: String): Boolean {
//        val numeric: Boolean
//        numeric = number.matches("-?\\d+(\\.\\d+)?".toRegex())
//        return if (number.length == BaaSConstants.BS_SP_OTP_LENGTH && numeric) true else false
//    }

    /* Bellow all tests will be tested
    With  service created we can now call its method that should
   consume the MockResponse above.
   assertion to check if the result is as expected.
        */
    @Test
    @Throws(IOException::class)
    fun testForSendOtpApi() {
        TestCase.assertEquals(
            "OTP_SENT",
            mockApiCaller!!.callForSendOtpApi("json_send_otp_success.json").execute()
                .body()!!.message
        )
    }

    @Test
    @Throws(IOException::class)
    fun testForVerifyOtpApi() {
        val call: Call<com.payu.baas.coreUI.nonUI.model.responseModels.VerifyOtpResponse> =
            mockApiCaller!!.callForVerifyOtpApi("json_verify_otp_success.json")
// test for correct response
        TestCase.assertEquals(
            "OTP_VERIFIED",
            call.execute().body()!!.message
        )
        // test for correct passed request body
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        val verifyOtp: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.model.model.VerifyOtp()
        verifyOtp.code = "1111"
        verifyOtp.identity = "8544941620"
        verifyOtp.type = "SignIn"
        TestCase.assertEquals("POST", recordedRequest!!.method)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.VerifyOtp::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.VerifyOtp
        TestCase.assertEquals(com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(verifyOtp), com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(reqBody))
    }
    @Test
    @Throws(IOException::class)
    fun testForVerifyOtpApi_WrongOtp() {
        val call: Call<com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse> =
            mockApiCaller!!.callForVerifyOtpApiWrongOtp("json_error_response.json")
// test for correct response
        TestCase.assertEquals(
            "Error",
            call.execute().body()!!.rawResponse
        )
        // test for correct passed request body
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        val verifyOtp: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.model.model.VerifyOtp()
        verifyOtp.code = "1111"
        verifyOtp.identity = "8544941620"
        verifyOtp.type = "SignIn"
        TestCase.assertEquals("POST", recordedRequest!!.method)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.VerifyOtp =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.VerifyOtp::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.VerifyOtp
        TestCase.assertEquals(com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(verifyOtp), com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(reqBody))
    }

    @After
    fun shutDown() {
        mockApiCaller!!.tearDownServer()
    }
}