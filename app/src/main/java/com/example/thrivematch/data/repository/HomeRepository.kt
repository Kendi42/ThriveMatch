package com.example.thrivematch.data.repository

import android.util.Log
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.AccountSetupAPI
import com.example.thrivematch.data.network.HomeDataAPI

class HomeRepository(
    private val api: HomeDataAPI
) : BaseRepository(){
    private var likedCardsList: MutableList<CardSwipeItemModel> = mutableListOf()

    suspend fun getBusinessCardData(): MutableList<CardSwipeItemModel>{
        val startupData= api.getStartupCardData().startups // type is List<StartupDataResponse.Startup>
        val cardSwipeItems = startupData.map { startup ->
            CardSwipeItemModel(
                name = startup.name,
                industry = startup.industry,
                description = startup.description,
                imageURL = startup.picturePath
            )
        }.toMutableList()

        return cardSwipeItems
    }

    suspend fun saveLikedCard(savedCard: CardSwipeItemModel){
        val response= api.saveLikedCard(savedCard)
        Log.i("Save Response", response.toString())
    }

    suspend fun getLikedCards(): MutableList<PendingMatchModel>{
        Log.i("LikedCardsRepo", api.getLikedCards().toString())
       return api.getLikedCards()
    }




//    private fun createSampleBusinessData(): MutableList<CardSwipeItemModel> {
//
//        val cardItems = mutableListOf<CardSwipeItemModel>()
//        // Adding sample data to the list
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "Bloom Energy",
//                industry = "Sustainable Energy",
//                description = "Harnessing the limitless potential of the sun, we're empowering individuals and businesses with clean, reliable, and sustainable energy solutions.",
//                imageURL = "https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais"
//            )
//        )
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "Jozzby",
//                industry = "Technology",
//                description = "We ignite the tech industry with disruptive solutions. Invest in Jozzby and fuel the future of limitless possibilities.",
//                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1aHK5iVcTAAOzjFUaxsUjJrp1atZtWHmwSHqrg7TXlQ&s"
//            )
//        )
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "BTech",
//                industry = "Technology",
//                description = "We ignite the tech industry with disruptive solutions. Invest in Btech and fuel the future of limitless possibilities.",
//                imageURL = "https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536"
//            )
//        )
//
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "CryptoFund",
//                industry = "Finance",
//                description = "Cryptofund is a pioneering financial company that specializes in digital asset investments, offering innovative solutions and expert guidance in the ever-evolving world of cryptocurrencies.",
//                imageURL = "https://cdn.dribbble.com/userupload/5454883/file/original-9725b8fe236a2dff4180dce8884d265a.jpg?compress=1&resize=1504x1128"
//            )
//        )
//
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "Ovido",
//                industry = "Entertainment",
//                description = " With a focus on pushing boundaries and redefining entertainment, Ovido delivers innovative games, immersive virtual reality experiences, and engaging multimedia productions, creating unforgettable moments of joy and adventure.",
//                imageURL = "https://cdn.dribbble.com/userupload/5831416/file/original-ca76f9b0181d2345353b6b3642743ab4.jpg?compress=1&resize=1504x1128"
//            )
//        )
//
//        cardItems.add(
//            CardSwipeItemModel(
//                name = "Drivable",
//                industry = "Transportation",
//                description = "Drivable is a forward-thinking transportation company that revolutionizes the way people move and commute. With a focus on sustainable and efficient solutions, they provide cutting-edge technologies and services for electric vehicles",
//                imageURL = "https://cdn.dribbble.com/userupload/8098458/file/original-a2252dbb9fbd1b6fc989cea4156f9519.jpg?compress=1&resize=1338x1003&vertical=center"
//            )
//        )
//
//                cardItems.add(
//            CardSwipeItemModel(
//                name = "ArrowHealth",
//                industry = "HealthCare",
//                description = "With a focus on leveraging cutting-edge advancements such as artificial intelligence and telemedicine, we at ArrowHealth aim to improve accessibility, efficiency, and quality of healthcare services",
//                imageURL = "https://cdn.dribbble.com/userupload/7889038/file/original-8a3114ac067714ed900bb8437175ec7c.jpg?compress=1&resize=1504x1128"
//            )
//        )
//
//        // Add more dating card items to the list
//        //        cardItems.add(
////            CardSwipeItemModel(
////                name = "",
////                industry = "",
////                description = "",
////                imageURL = ""
////            )
////        )
//
//        return cardItems
//
//    }

}