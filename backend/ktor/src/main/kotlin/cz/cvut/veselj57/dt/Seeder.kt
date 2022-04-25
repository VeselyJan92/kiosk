/*
package cz.cvut.veselj57.dt

import at.favre.lib.crypto.bcrypt.BCrypt
import com.thedeanda.lorem.LoremIpsum
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.TripDAO
import io.ktor.util.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


val id = "624837e9e377490f7e93f3e5"


val x="""
    Snídaně

    Snídaně 8:00 - 9:30 formou bufetu

    Prosíme hosty, aby neodnášeli jídlo ze snídaně na pokoje


    Obědy, večeře

            V penzionu nepodáváme.

    Doporučujeme navštívit hotel Atlas nebo penzion Relax v centru Velké Úpy


    Bar

            Veškerá nabídka baru je na objednávku u domácích

    Nápoje Vám přineseme do kulečníku i na terasu


    Wellness

            Wellness je na objednávku, začínáme v celou hodinu v rozmezí 16-21:00

            Minimální počet osob 2, maximální počet osob 5

            Cena za hodinu pro 2 osoby 400Kč, pro 3-5 osob 600Kč

            Děti mladší 10 let mohou být ve vířivce jen pod dohledem rodičů.


    Fittness

            Cvičit můžete zdarma do doby, než bude v provozu sauna nebo vířivka

            Posilovací stroje mohou používat pouze osoby od 15 let.

            Je zakázáno používat stroje v průběhu provozu wellness.


    Kulečník a stolní tenis

            Otevřeno po celý den. Neodnášejte prosím pálky a míčky

    Nechávejte je místě, stejně i tágo a koule

    Kulečník smí používat jen osoby od 10 let


    Mikrokuchyňka

            Na patře u schodů je umístěna mikrokuchyňka se společnou lednicí

            Je určena výhradně pro přípravu kávy, čaje nebo jídla pro kojence

            Nádobí neposkytujeme


    Parkování

            Parkujte vždy od spodu na šikmo tak, abyste zabrali co nejméně místa

            Jeďte co nejvíce dopředu, aby kufr auta nepřekážel na silnici

    Spolujezdec řidiči vždy ukáže, kam až může zajet

    Při výjezdu vycouvejte až na křižovatku a pak po předu dolů

            Zákaz otáčení aut u penzionu, po příjezdové cestě jezděte pomalu


    Domácí obuv

            V penzionu používejte domácí obuv

            Venkovní obuv nechávejte v botárně nebo ve vstupní hale


    Wifi

            Jméno:        HOST

    Heslo:                qazqazqaz

    Na celém penzionu zdarma, rychlost je limitována
""".trimIndent()


class Seeder(): KoinComponent {

    val db by inject<MongoDB>()
    val tripDAO by inject<TripDAO>()



    suspend fun seedHotel(): String {

        val lorem = LoremIpsum.getInstance()


        val imgs = listOf(
            javaClass.classLoader.getResource("seed/pecka.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/skiresort.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/relax.jpg")!!.openStream().readAllBytes(),
        )



        suspend fun getTrips() = buildList {
            repeat(4){
                add(tripDAO.insertTrip(id, lorem.getTitle(4, 9), lorem.getHtmlParagraphs(1, 2), imgs.random(), imgs.shuffled(), lorem.getWords(2, 4).split(" ")))
            }
        }


        db.hotels.insertOne(HotelEntity(
            _id = id,
            hashed_password = BCrypt.withDefaults().hash(10, "secret".toByteArray(Charsets.UTF_8)).encodeBase64(),
            hotel_name = "Penzion U Veselých",
            email = "jan.vesely92@gmail.com",
            accommodation_text = x,
            contact_email = "asd",
            contact_phone = "asd",
            official_website = "asd",
            trip_categories = listOf(
                TripCategoryEntity(name = "Pro děti", trips_ids = getTrips()),
                TripCategoryEntity(name = "V okolí", trips_ids = getTrips()),
                TripCategoryEntity(name = "Autem", trips_ids = getTrips()),
                TripCategoryEntity(name = "Turistika", trips_ids = getTrips()),
                TripCategoryEntity(name = "Relax", trips_ids = getTrips()),
                TripCategoryEntity(name = "Po příjezdu", trips_ids = getTrips()),
            )
        ))


        repeat(4){
            db.posts.insertOne(TravelInfoEntity(
                hotel_id = id,
                title = lorem.getTitle(4, 9),
                text = lorem.getHtmlParagraphs(1, 2)
            ))

        }

        return id
    }


}*/
