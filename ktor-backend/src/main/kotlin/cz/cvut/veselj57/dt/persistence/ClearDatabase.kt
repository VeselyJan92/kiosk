package cz.cvut.veselj57.dt.persistence

import cz.cvut.veselj57.dt.Seeder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatabaseDebugUtils: KoinComponent {

    val db by inject<MongoDB>()

    suspend fun clear(){
        db.hotels.deleteMany()
        db.trips.deleteMany()
        db.images.drop()
        db.posts.drop()
    }

    suspend fun seed() {
        val seeder = Seeder()

        seeder.seedHotel()
    }

}