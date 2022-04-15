package cz.cvut.veselj57.dt.plugins




import KtorGraphQLServer
import cz.cvut.veselj57.dt.persistence.MongoDBImpl
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.repository.ImageDAO
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import cz.cvut.veselj57.dt.services.AuthService
import cz.cvut.veselj57.dt.services.NewHotelService
import io.ktor.server.application.*
import io.ktor.util.*
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.logger.SLF4JLogger
import java.util.logging.Logger


// Create a new custom application plugin
internal class CustomKoinPlugin(internal val koinApplication: KoinApplication) {

    // Implements ApplicationPlugin as a companion object.
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, KoinApplication, CustomKoinPlugin> {
        // Creates a unique key for the plugin.
        override val key = AttributeKey<CustomKoinPlugin>("CustomKoinPlugin")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: KoinApplication.() -> Unit,
        ): CustomKoinPlugin {
            val koinApplication = startKoin(appDeclaration = configure)

            println("LOGGGGGGGGGgggggg")

            pipeline.environment?.monitor?.subscribe(ApplicationStopping) {
                stopKoin()
            }

            return CustomKoinPlugin(koinApplication)
        }

    }
}



fun Application.configureKoin(){

    install(CustomKoinPlugin) {
        val persistence = module {
            single { MongoDBImpl.getFromApplicationConfig(this@configureKoin)  }
        }


        val core = module {
            single<ImageDAO> { ImageDAO() }
            single<TripDAO> { TripDAO() }
            single<TravelInfoTextDAO> { TravelInfoTextDAO() }
            single<HotelDAO> { HotelDAO() }
            single<AuthService> { AuthService() }
            single<NewHotelService> { NewHotelService() }
            single<KtorGraphQLServer> { KtorGraphQLServer.get() }
        }

        SLF4JLogger()
        modules(core)
        modules(persistence)
    }

}