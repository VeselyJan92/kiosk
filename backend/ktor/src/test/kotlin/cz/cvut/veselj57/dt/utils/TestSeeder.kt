package cz.cvut.veselj57.dt.utils

import com.thedeanda.lorem.LoremIpsum
import cz.cvut.veselj57.dt.graphql.model.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.model.mutations.HotelSettingsUpdate
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTravelInfo
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTrip
import io.ktor.util.*

object TestSeeder {

    val lorem = LoremIpsum.getInstance()


    fun getTestImage(name: String = "seed/pecka.jpg") = javaClass.classLoader.getResource(name)!!.openStream().readAllBytes().encodeBase64()


    fun getHotelRegistration(
        email: String,
        password: String ,
        accommodation_text: String = lorem.getHtmlParagraphs(1, 2),
        contact_phone: String = lorem.phone,
        contact_email: String = lorem.email,
        official_website: String = lorem.url,
    ) = HotelMutation.RegisterHotel(email,
        password,
        accommodation_text,
        contact_phone,
        contact_email,
        official_website
    )


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

    fun getHotelSettings(
        _id: String,
        hotel_name: String = lorem.getWords(2),
        logo_img: String = getTestImage(),
        intro_img: String = getTestImage(),
        accommodation_text: String = lorem.getHtmlParagraphs(1, 2),
        contact_phone: String = lorem.phone,
        contact_email: String = lorem.email,
        official_website: String = lorem.url,
    ) = HotelSettingsUpdate( _id, hotel_name,intro_img, logo_img, accommodation_text, contact_phone, contact_email, official_website)

    fun getUpsertTravelInfo(
        _id: String? = null,
        title: String = lorem.getTitle(2, 6),
        text: String = lorem.getHtmlParagraphs(1, 2)
    ) = UpsertTravelInfo(_id, title, text)


}