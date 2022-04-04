package cz.cvut.veselj57.dt

sealed class Role() {

    object MasterAccess: Role()

    data class Hotel(val id: String): Role()

    data class HotelVisitor(val id: String): Role()

}