package com.example.thrivematch.data.network

import android.util.Log
import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockBehavior
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.AccountSetupResponse
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.data.response.SignupResponse
import com.example.thrivematch.data.response.StartupDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeDataAPI {



    @GET("v1/all_startups")
    suspend fun getStartupCardData(
    ): StartupDataResponse

//
//    @POST("v1/userRegister")
//    suspend fun signup(
//        @Body signupRequest: SignupRequest
//    ): SignupResponse
//

//
//    @Mock
//    @MockResponse(body =
//    "{\"message\":\"Account Setup Successfully !!\"," +
//            "\"startups\":[" +
//            "{\"id\": 1,\"description\":\"Harnessing the limitless potential of the sun, we're empowering individuals and businesses with clean, reliable, and sustainable energy solutions.\",\"industry\":\"Sustainable Energy\",\"name\":\"Bloom Energy\",\"picturePath\":\"http://res.cloudinary.com/dkkxufuqp/image/upload/v1689850620/gambdf2ut5hjz3dtfwl3.png\"}," +
//            "{\"id\": 2,\"description\":\"We ignite the tech industry with disruptive solutions. Invest in Jozzby and fuel the future of limitless possibilities.\",\"industry\":\"Technology\",\"name\":\"Jozzby\",\"picturePath\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1aHK5iVcTAAOzjFUaxsUjJrp1atZtWHmwSHqrg7TXlQ&s\"}," +
//            "{\"id\": 3,\"description\":\"We ignite the tech industry with disruptive solutions. Invest in Btech and fuel the future of limitless possibilities.\",\"industry\":\"Technology\",\"name\":\"BTech\",\"picturePath\":\"https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536\"}," +
//            "{\"id\": 4,\"description\":\"Cryptofund is a pioneering financial company that specializes in digital asset investments, offering innovative solutions and expert guidance in the ever-evolving world of cryptocurrencies.\",\"industry\":\"Finance\",\"name\":\"CryptoFund\",\"picturePath\":\"https://cdn.dribbble.com/userupload/5454883/file/original-9725b8fe236a2dff4180dce8884d265a.jpg?compress=1&resize=1504x1128\"}," +
//            "{\"id\": 5,\"description\":\"With a focus on pushing boundaries and redefining entertainment, Ovido delivers innovative games, immersive virtual reality experiences, and engaging multimedia productions, creating unforgettable moments of joy and adventure.\",\"industry\":\"Entertainment\",\"name\":\"Ovido\",\"picturePath\":\"https://cdn.dribbble.com/userupload/5831416/file/original-ca76f9b0181d2345353b6b3642743ab4.jpg?compress=1&resize=1504x1128\"}," +
//            "{\"id\": 6,\"description\":\"Drivable is a forward-thinking transportation company that revolutionizes the way people move and commute. With a focus on sustainable and efficient solutions, they provide cutting-edge technologies and services for electric vehicles\",\"industry\":\"Transportation\",\"name\":\"Drivable\",\"picturePath\":\"https://cdn.dribbble.com/userupload/8098458/file/original-a2252dbb9fbd1b6fc989cea4156f9519.jpg?compress=1&resize=1338x1003&vertical=center\"}," +
//            "{\"id\": 7,\"description\":\"With a focus on leveraging cutting-edge advancements such as artificial intelligence and telemedicine, we at ArrowHealth aim to improve accessibility, efficiency, and quality of healthcare services\",\"industry\":\"HealthCare\",\"name\":\"ArrowHealth\",\"picturePath\":\"https://cdn.dribbble.com/userupload/7889038/file/original-8a3114ac067714ed900bb8437175ec7c.jpg?compress=1&resize=1504x1128\"}]," +
//            "\"success\":true}")
//    @MockBehavior(durationMillis = 0, durationDeviation = 0)
//    @POST("v1/getStartupData")
//    suspend fun getStartupCardData(
//    ): StartupDataResponse
//
    @Mock
    @MockResponse(body = "{\"message\":\"Successfully Saved !!\",\"success\":true}")
    @POST("v1/saveLikedCard")
    suspend fun saveLikedCard(
        @Body savedCard: CardSwipeItemModel
    ): SignupResponse

    @Mock
    @POST("v1/getLikedCards")
    @MockResponse(body = "[{\"imageURL\":\"https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais\",\"name\":\"Bloom Energy\"}," +
            "{\"imageURL\":\"https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536\",\"name\":\"BTech\"}]")
    @MockBehavior(durationMillis = 0, durationDeviation = 0)
    suspend fun getLikedCards(): MutableList<PendingMatchModel>

}