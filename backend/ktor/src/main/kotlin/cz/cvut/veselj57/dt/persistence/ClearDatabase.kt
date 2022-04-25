package cz.cvut.veselj57.dt.persistence

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

}