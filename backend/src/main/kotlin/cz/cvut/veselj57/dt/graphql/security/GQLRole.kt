package cz.cvut.veselj57.dt.graphql.security

import cz.cvut.veselj57.dt.entities.HotelEntity

sealed class GQLRole {
    data class Hotel(val _id: String, val entity: HotelEntity): GQLRole()
    object Visitor: GQLRole()
}