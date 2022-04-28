package cz.cvut.veselj57.dt


import com.expediagroup.graphql.generator.extensions.print
import cz.cvut.veselj57.dt.api.APIRouteGraphQL
import cz.cvut.veselj57.dt.api.APIRouteImages
import cz.cvut.veselj57.dt.api.APIRoutesAuthentication
import cz.cvut.veselj57.dt.graphql.GraphQLSchema
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.get
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberExtensionFunctions


fun main(args: Array<String>){
    io.ktor.server.cio.EngineMain.main(args)
}


fun Application.main() {
    //configureRouting()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureKoin()



    runBlocking {

        val db = get<MongoDB>()

        db.hotels.deleteMany()
        db.trips.deleteMany()
        db.imgs.drop()
        db.posts.drop()

        SeederReal().seedHotel()
    }





    APIRoutesAuthentication()
    APIRouteImages()
    APIRouteGraphQL()


    GraphQLSchema.graphQLSchema.print()

}


























