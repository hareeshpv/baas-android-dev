package com.payu.baas.coreUI

import com.payu.baas.core.model.model.*
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

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class OnBoardingUnitTests {
    var mockApiCaller: MockApiCallManger? = null

    @Before
    fun setUp() {
        mockApiCaller = MockApiCallManger()
        mockApiCaller!!.initialize()
    }

    /* Bellow all tests will be tested
    With  service created we can now call its method that should
   consume the MockResponse above.
   assertion to check if the result is as expected.
        */
    @Test
    @Throws(IOException::class)
    fun testForSaveAddressApi() {
        TestCase.assertEquals(
            "Details have been saved and user onboarded successfully",
            mockApiCaller!!.callForSaveAddressApi("json_save_address_success.json").execute()
                .body()!!.message
        )

        // test for correct request body passed
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(20, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel
//        TestCase.assertNotNull( JsonUtils.toString(reqBody))
        TestCase.assertNotNull(reqBody.panNumber)
    }

    @Test
    @Throws(IOException::class)
    fun testForSaveAddressApi_MisiingFields() {
        val call: Call<com.payu.baas.coreUI.nonUI.model.responseModels.SaveAddressResponse> =
            mockApiCaller!!.callForSaveAddressApi_MissingFields("json_save_address_success.json")
        call.execute()
        // test for correct request body passed
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(20, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        TestCase.assertEquals("GET", recordedRequest!!.method) // check method type
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel
        TestCase.assertNotNull(reqBody.panNumber) //THROWS AssertionFailedError
    }

    @Test
    @Throws(IOException::class)
    fun testForSaveAddressApi_WrongFieldType() {
        val call: Call<com.payu.baas.coreUI.nonUI.model.responseModels.SaveAddressResponse> =
            mockApiCaller!!.callForSaveAddressApi_WrongFieldType("json_save_address_success.json")
        call.execute()
        // test for correct request body passed
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest!!.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.SaveAddressRequestModel
        TestCase.assertEquals(reqBody.mobile, "8544941620")
    }

    @Test
    @Throws(IOException::class)
    fun testForEmployementVerificationApi() {
        TestCase.assertEquals(
            "EMPLOYEMENT_VERIFIED",
            mockApiCaller!!.callForEmploymentVerificationApi("json_employee_verification_success.json")
                .execute()
                .body()!!.userMessage
        )
        // test for correct request body passed
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.EmploymentVerificationRequestModel
        TestCase.assertNotNull(com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(reqBody))
    }

    @Test
    @Throws(IOException::class)
    fun testForEmployementVerificationApi_WrongFieldType() {
        // wronng fields
        TestCase.assertEquals(
            "EMPLOYEMENT_VERIFIED",
            mockApiCaller!!.callForEmploymentVerificationApi_WrongFieldType(
                "json_employee_verification_error_response.json"
            ).execute()
                .body()!!.userMessage
        )
    }

    // PAN doesn't match with backend
    @Test
    @Throws(IOException::class)
    fun testForPanMismatchInEmployementVerificationApi_WrongFieldType() {
        // wronng fields
        TestCase.assertEquals(
            "Sorry, Please enter correct PAN number ",
            mockApiCaller!!.callForEmploymentVerificationApi_WrongFieldType(
                "json_employee_verification_pan_error_response.json"
            ).execute()
                .body()!!.userMessage
        )
    }

    // user logged in with wrong mobile number an dtries to verify
    @Test
    @Throws(IOException::class)
    fun testForMobileMismatchInEmployementVerificationApi_WrongFieldType() {
        // wronng fields
        TestCase.assertEquals(
            "Sorry, Please enter your employer registered mobile number.s",
            mockApiCaller!!.callForEmploymentVerificationApi_WrongFieldType(
                "json_employee_verification_mobile_error_response.json"
            ).execute()
                .body()!!.userMessage
        )
    }

    @Test
    @Throws(IOException::class)
    fun testForValidatePanApi() {
        TestCase.assertEquals(
            "Pan Number Verified",
            mockApiCaller!!.callForValidatePanApi("json_pan_success_response.json")
                .execute()
                .body()!!.message
        )
        // test for correct request body passed
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        recordedRequest.path
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.ValidatePanRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.ValidatePanRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.ValidatePanRequestModel
        TestCase.assertNotNull(com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(reqBody))
    }

    @Test
    @Throws(IOException::class)
    fun testForSetPasscode() {
        TestCase.assertEquals(
            "Password Updated Sucessfully",
            mockApiCaller!!.callForSetPasscodeApi(
                "json_set_passcode_success.json"
            ).execute()
                .body()!!.msg
        )
        //for getting request path and then checking the passcode
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        var requestPath: String = recordedRequest.path!!
        var newPasscode = requestPath.replace("/passcode?newPasscode=", "")
        newPasscode = newPasscode.replace("&oldPasscode=8989", "")
        TestCase.assertTrue(isValidPasscode(newPasscode))
        // for testing failure case when passcode is more than 4 digit
        newPasscode = newPasscode + "5"
        TestCase.assertEquals(true, isValidPasscode(newPasscode))
    }

    /*
    check if passcode is 4 digit numeric only
     */
    fun isValidPasscode(number: String): Boolean {
        val numeric: Boolean
        numeric = number.matches("-?\\d+(\\.\\d+)?".toRegex())
        return if (number.length == com.payu.baas.coreUI.nonUI.util.BaaSConstants.BS_SP_OTP_LENGTH && numeric) true else false
    }

    @Test
    @Throws(IOException::class)
    fun testForLogin() {
        TestCase.assertNotNull(
            mockApiCaller!!.callForLoginApi(
                "json_login_success.json"
            ).execute().body()!!.accessToken
        )
        // test for correct request body passed (correct password format)
        val recordedRequest: RecordedRequest? =
            mockApiCaller!!.mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        TestCase.assertEquals("POST", recordedRequest!!.method)
        val reqBody: com.payu.baas.coreUI.nonUI.model.model.LoginRequestModel =
            com.payu.baas.coreUI.nonUI.util.JsonUtils.toObject(
                recordedRequest.body.readUtf8().toString(),
                com.payu.baas.coreUI.nonUI.model.model.LoginRequestModel::class.java
            ) as com.payu.baas.coreUI.nonUI.model.model.LoginRequestModel
        TestCase.assertNotNull(com.payu.baas.coreUI.nonUI.util.JsonUtils.toString(reqBody))
        TestCase.assertEquals(reqBody.passcode, "1111")
        TestCase.assertEquals(reqBody.passcode, "111") //wrong passcode
    }

    @After
    fun shutDown() {
        mockApiCaller!!.tearDownServer()
    }
}