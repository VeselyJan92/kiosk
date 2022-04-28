package cz.cvut.veselj57.dt.graphql.model.mutations

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface

interface HotelMutation {

    @TypeScriptInterface
    @kotlinx.serialization.Serializable
    data class RegisterHotel(
        val email: String,
        val password: String,
        val accommodation_text: String,
        val contact_phone: String,
        val contact_email: String,
        val official_website: String,
    )

}