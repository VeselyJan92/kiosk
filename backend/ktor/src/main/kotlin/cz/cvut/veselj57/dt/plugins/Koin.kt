package cz.cvut.veselj57.dt.plugins




import KtorGraphQLServer
import cz.cvut.veselj57.dt.persistence.MongoDBImpl
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.repository.ImageDAO
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import cz.cvut.veselj57.dt.services.AuthService
import cz.cvut.veselj57.dt.services.NewHotelService
import cz.cvut.veselj57.dt.services.ServerConfig
import io.ktor.server.application.*
import io.ktor.util.*
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger
import java.util.logging.Logger




fun Application.configureKoin(){

    val config = this@configureKoin.environment.config

    install(Koin) {
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
            single<ServerConfig> { ServerConfig(config.property("deployment.base_url").getString()) }
        }

        SLF4JLogger()
        modules(core)
        modules(persistence)
    }

}