package cz.cvut.veselj57.dt.utils

import cz.cvut.veselj57.dt.graphql.model.mutations.TravelInfoMutation
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTrip
import cz.cvut.veselj57.dt.graphql.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.mutations.TripMutation
import io.ktor.util.*

object TestSeeder {

    fun getHotelRegistration(
        email: String,
        password: String ,
        accommodation_text: String = "",
        contact_phone: String = "",
        contact_email: String = "",
        official_website: String = "",
    ) = TravelInfoMutation.RegisterHotel(email,
        password,
        accommodation_text,
        contact_phone,
        contact_email,
        official_website)


    fun getUpsertTripData(
        id: String? = null,
        title: String= "Trip title",
        text: String = "text",
        imgs: List<String>? = null,
        tags: List<String> = listOf("nature", "cycling"),
        categories: List<String> = listOf()
    ): UpsertTrip {
        val seedImages = listOf("seed/pecka.jpg", "seed/bike.jpg", "seed/castle.jpg").map {
            "blob:" + javaClass.classLoader.getResource(it)!!.openStream().readAllBytes().encodeBase64()
        }

        return UpsertTrip(
            id, title, text, imgs ?: seedImages, tags, categories
        )

    }


}