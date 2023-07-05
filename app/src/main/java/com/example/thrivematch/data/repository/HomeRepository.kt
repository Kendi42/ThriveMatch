package com.example.thrivematch.data.repository

import com.example.thrivematch.data.models.CardSwipeItemModel

class HomeRepository : BaseRepository(){

    fun getBusinessCardData(): MutableList<CardSwipeItemModel>{
        return createSampleBusinessData()
    }


    private fun createSampleBusinessData(): MutableList<CardSwipeItemModel> {

        val cardItems = mutableListOf<CardSwipeItemModel>()
        // Adding sample data to the list
        cardItems.add(
            CardSwipeItemModel(
                name = "Bloom Energy",
                industry = "Sustainable Energy",
                description = "Harnessing the limitless potential of the sun, we're empowering individuals and businesses with clean, reliable, and sustainable energy solutions.",
                imageURL = "https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais"
            )
        )
        cardItems.add(
            CardSwipeItemModel(
                name = "Jozzby",
                industry = "Technology",
                description = "We ignite the tech industry with disruptive solutions. Invest in Jozzby and fuel the future of limitless possibilities.",
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1aHK5iVcTAAOzjFUaxsUjJrp1atZtWHmwSHqrg7TXlQ&s"
            )
        )
        cardItems.add(
            CardSwipeItemModel(
                name = "BTech",
                industry = "Technology",
                description = "We ignite the tech industry with disruptive solutions. Invest in Btech and fuel the future of limitless possibilities.",
                imageURL = "https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536"
            )
        )
        // Add more dating card items to the list
        return cardItems

    }
}