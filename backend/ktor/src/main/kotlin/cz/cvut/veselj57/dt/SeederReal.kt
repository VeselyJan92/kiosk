package cz.cvut.veselj57.dt

import at.favre.lib.crypto.bcrypt.BCrypt
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.ImageDAO
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import io.ktor.util.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


val text="""
<p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Snídaně</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Snídaně 8:00 - 9:30 formou bufetu. Prosíme hosty, aby neodnášeli jídlo ze snídaně na pokoje</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Obědy, večeře</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">V penzionu nepodáváme. Doporučujeme navštívit hotel Atlas nebo penzion Relax v centru Velké Úpy</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Bar</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Veškerá nabídka baru je na objednávku u domácích. Nápoje Vám přineseme do kulečníku i na terasu</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Wellness</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Wellness je na objednávku, začínáme v celou hodinu v rozmezí 16-21:00. Minimální počet osob 2, maximální počet osob 5. Cena za hodinu pro 2 osoby 400Kč, pro 3-5 osob 600Kč. Děti mladší 10 let mohou být ve vířivce jen pod dohledem rodičů.</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Fittness</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Cvičit můžete zdarma do doby, než bude v provozu sauna nebo vířivka. Posilovací stroje mohou používat pouze osoby od 15 let. Je zakázáno používat stroje v průběhu provozu wellness.</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Kulečník a stolní tenis</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Otevřeno po celý den. Neodnášejte prosím pálky a míčky. Nechávejte je místě, stejně i tágo a koule. Kulečník smí používat jen osoby od 10 let</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Mikrokuchyňka</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Na patře u schodů je umístěna mikrokuchyňka se společnou lednicí. Je určena výhradně pro přípravu kávy, čaje nebo jídla pro kojence. Nádobí neposkytujeme</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Parkování</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">Parkujte vždy od spodu na šikmo tak, abyste zabrali, co nejméně místa. Jeďte co nejvíce dopředu, aby kufr auta nepřekážel na silnici. Spolujezdec řidiči vždy ukáže, kam až může zajet. Při výjezdu vycouvejte až na křižovatku a pak po předu dolů. Zákaz otáčení aut u penzionu, po příjezdové cestě jezděte pomalu&nbsp;</span></p><p><strong style="background-color: transparent; color: rgb(0, 0, 0);">Domácí obuv</strong></p><p><span style="background-color: transparent; color: rgb(0, 0, 0);">V penzionu používejte domácí obuv. Venkovní obuv nechávejte v botárně nebo ve vstupní hale</span></p></p>
""".trimIndent()



class SeederReal(): KoinComponent {

    val db by inject<MongoDB>()
    val tripDAO by inject<TripDAO>()
    val travelInfoTextDAO by inject<TravelInfoTextDAO>()
    val imageDAO by inject<ImageDAO>()

    var hotelId = "626271daa3f7f53cbf3a10d9"

    suspend fun getImg(path: String) = javaClass.classLoader.getResource("real/$path")!!.openStream().readAllBytes()

    suspend fun seedHotel(): String {

        println(hotelId)

        db.hotels.insertOne(HotelEntity(
            _id = hotelId,
            hashed_password = BCrypt.withDefaults().hash(10, "secret".toByteArray(Charsets.UTF_8)).encodeBase64(),
            email = "jan.vesely92@gmail.com",
            hotel_name = "Penzion U Veselých",
            intro_img_id = imageDAO.putImage(getImg("hotel.jpg")),
            logo_img_id = imageDAO.putImage(getImg("logo_tmp.png")),
            accommodation_text = text,
            contact_email = "veselypenzion@email.cz",
            contact_phone = "774 406 784",
            official_website = "veselypenzion.cz",
            trip_categories = listOf(
                car(), turism(), attractions(), turism(), children()
            )
        ))


        info()

        return hotelId
    }

    suspend fun info(){

        travelInfoTextDAO.upsert(TravelInfoEntity(
            hotel_id = hotelId,
            title = "TourPAS – využijte lanovky bez omezeného počtu jízd",
            text = """
                TourPAS je 1 - 4 denní jízdenka, která Vám dovolí využívat lanovky na Černou horu, Portášky a Hnědý vrch bez omezení za paušální cenu.
                Výhodné pro rodiny s dětmi, důchodce a všechny, kteří nechtějí daleko chodit, ale přesto tak navštíví vrcholové partie Krkonoš.  Využijete i pro návštěvu Herní krajiny Pecka na Portáškách, jízdu na koloběžkách či kolech

                Dospělí 	od 2005			550 Kč
                Senior 	od 1959			450 Kč
                Junior 	2004 - 2009			450 Kč
                Dítě		2010 - 2015			350 Kč
                Malé dítě	2016 a mladší		zdarma
                """.trimIndent()
        ))

        travelInfoTextDAO.upsert(TravelInfoEntity(
            hotel_id = hotelId,
            title = "Návštěva Sněžky lanovkou",
            text = """
                	Návštěva vrcholu Sněžky patří k základním bodům každého pobytu. Na Sněžku vede lanová dráha složená ze dvou úseků. Horní úsek je často mimo provoz z důvodu větru.  Od penzionu jedete autobusem na konečnou v Peci  5 min / 10Kč a odtud 1,5km pěšky na lanovku. Můžete jet i autem, na zásadní křižovatce doprava na parkoviště u Kapličky. Autem jeďte až téměř k lanovce, neboť na každé straně silnice lze parkovat. Cena za parkování 3 hod / 120 Kč a celý den 240 Kč.
                        Při návštěvě Sněžky zejména v červenci, srpnu, víkendech červen a září důrazně doporučujeme být na lanovce alespoň do 9:30, jinak skončíte ve vícehodinových frontách.
                        Lanovkou lze zkrátit výstup pěšky na vrchol viz sekce výlety
                    
                    Zkrácený ceník, obousměrný lístek až na vrchol
                                            prázdniny		ostatní měsíce
                    Dospělí 	od 18 let			620 Kč		520 Kč
                    Senior 	od 65 let			550 Kč		470 Kč
                    Junior 	12 - 18 let			560 Kč		470 Kč
                    Dítě		5 - 12 let			350 Kč		250 Kč
                    Malé dítě	do 5 let			zdarma		zdarma
                    """.trimIndent()
        ))

        travelInfoTextDAO.upsert(TravelInfoEntity(
            hotel_id = hotelId,
            title = "Relax Park v Peci pod Sněžkou",
            text = """
                	V Peci pod Sněžkou cestou na lanovku na Sněžku je umístěna bobová dráha o délce 900m a areál Lemurie pro malé i velké děti plný prolézaček, dlouhých adrenalinových skluzavek, velikých trampolín a s nafukovacím hradem.

                    Bobová dráha 	1 jízda		6 jízd		10 jízd
                    Osoba nad 140cm	150 Kč	700 Kč	1100 Kč
                    Osoba do 140cm	110 Kč	450 Kč	750 Kč
                    Jízdenka je přenosná
                    
                    Lemurie		dítě 300Kč / den
                    """.trimIndent()
        ))

        travelInfoTextDAO.upsert(TravelInfoEntity(
            hotel_id = hotelId,
            title = "Jízda na koloběžkách",
            text = """
                	Můžete si vypůjčit koloběžku a jet z Portášek naproti penzionu 5 km nebo z Černé hory 10,5 km dolů. Půjčovny koloběžek jsou na dolních i horních stanicích lanovek. Pokud máte TourPASS, půjčte si jen koloběžku.
                    Cena za 1 jízdu na lanovce.
                    
                    Koloběžka		+ Portášky		+ Černá hora
                    300 / 270***		500 / 430***		430 / 350***
                    
                    *** platí při online nákupu
                    """.trimIndent()
        ))

    }



    suspend fun children(): TripCategoryEntity {
        val ids = listOf(
            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Herní krajina Pecka",
                text = """
                        Nikde jinde než ve Velké Úpě na Portáškách (lanovkou naproti penzionu) se nemůžete rozhlédnout z paroží jelena, projít 15 metrů dlouhou dřevěnou sochou rysa, nebo přejít po lanové stezce mezi čapími hnízdy. Objevte obří mravence a vřetenovku, nory zvířat, nebo velkého dřevěného mloka.
                        Zde strávíte mnoho krásných hodin s Vašimi ratolestmi. Vhodné zejména pro děti do 10 let, vstup zdarma, doprava lanovkou na Portášky, výhodně využijete TourPASS
                        link: https://leto.skiresort.cz/letni-aktivity/herni-krajina-pecka/
                        """.trimIndent(),
                introImage = getImg("pecka.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Dětský park kabinka",
                text = """
                        Nikde jinde než ve Velké Úpě na Portáškách (lanovkou naproti penzionu) se nemůžete rozhlédnout z paroží jelena, projít 15 metrů dlouhou dřevěnou sochou rysa, nebo přejít po lanové stezce mezi čapími hnízdy. Objevte obří mravence a vřetenovku, nory zvířat, nebo velkého dřevěného mloka.
                        Zde strávíte mnoho krásných hodin s Vašimi ratolestmi. Vhodné zejména pro děti do 10 let, vstup zdarma, doprava lanovkou na Portášky, výhodně využijete TourPASS
                        link: https://leto.skiresort.cz/letni-aktivity/herni-krajina-pecka/
                        """.trimIndent(),
                introImage = getImg("kabinka.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Dětské hřiště ve Velké Úpě a stezka ke Krakonošově zpovědnici",
                text = """
                            Sejdete od penzionu doprava na hlavní silnici a po 100m uvidíte malé dětské hřiště pro nejmenší návštěvníky s několika atrakcemi vhodné i pro nejmenší děti.
                            Pohádková stezka začíná v Peci a pokračuje po staré cestě směrem na Velkou Úpu. Cestou ke zpovědnici si děti pohrají na 8 herních zastaveních. Nejsou od sebe daleko, takže nožky mezi jednotlivými zastávkami ani nezačnou bolet. Kdo by si nechtěl zahrát piškvorky nebo lodě, vyřešit žabí bludiště či hlavolam, nebo sestřelit zajíce?  
                            link: https://www.krkonose.eu/ke-krakonosove-zpovednici
                        """.trimIndent(),
                introImage = getImg("zpovednici.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            )
        )

        return TripCategoryEntity(name = "Pro děti", trips_ids =  ids)
    }

    suspend fun attractions(): TripCategoryEntity {
        val ids = listOf(
            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Stezka korunami stromů",
                text = """
                        Stezka korunami stromů Krkonoše s jedinečnou podzemní jeskyní, vám nabídne nejen příjemnou procházku, spoustu zajímavých informací, ale i dechberoucí výhled do nádherné krajiny Krkonošského národního parku. Stezka zakončená vyhlídkovou věží o výšce 45 metrů se nachází na okraji Janských Lázní. Vstupné:

                        Dospělí 			320 Kč
                        Děti a senioři 		250 Kč
                        Rodina 2+1. 2+2		870 Kč
                        
                        ke stezce se dostanete buď autem (parkování přímo u stezky) nebo autobusem v 10:57 jezdí denně. 
                        Návštěvu stezky můžete spojit s výletem na Černou horu, koloběžkami nebo spěším výletem z Černé hory přes rašeliniště di Velké Úpy
                        """.trimIndent(),
                introImage = getImg("stezka.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Naučná stezka Vápenická stezka 1 km a stezka svaté Barbory 5 km",
                text = """
                        Pokud pojedete do Vrchlabí nebo na stezku korunami stromů, můžete cestou navštívit Vápenickou stezku, která je dlouhá pouhých 900m. Na 5 zastaveních se dočtete o těžbě vápence v Krkonoších, navštívíte dvě staré vápenky a uvidíte i do vápencového lomu. Děti budou mít radost.
                        V okolí jsou ještě další tři naučné stezky. Doporučujeme Naučnou stezku svaté Barbory 5km, krásný výlet s pěknými výhledy. Na stezce okolo lomu i Berghausu toho moc neuvidíte
                        """.trimIndent(),
                introImage = getImg("vapenka.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Naučná stezka Vlčí jáma 3 km",
                text = """
                            Od parkoviště Zelený potok nebo od autobusového nádraží se vydáte směrem vzhůru k lanovce na Hnědém vrchu. Pak chvíli po sjezdovce nahoru, až po levé straně uvidíte odbočku na úzkou pěšinu přes boudu Vlčí mlýn až ke krásnému karu Vlčí jáma se dvěma vodopády. Pak jdete dál na Velkou pláň s krásnými výhledy na hřeben Studniční hory a Sněžku  přes zbytkystaré klauzy na plavení dřeva zpět k autu. Tato varianta je hezčí než originál
                        """.trimIndent(),
                introImage = getImg("vlk.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Výlet na Jelení louky 5  km",
                text = """
                        Z parkoviště Zelený potok v Peci překročíte dole most a dáte se pěšky údolím kolem Zeleného potoka s několika malými vodopády a kaskádami. Cesta vede po lesní pěšině. Po dvou a půl kilometrech se údolí rozšiřuje, protože tu vysokohorský ledovec před desítkami tisíc let vyhloubil široký ledovcový kar. V 16. století zde byla založena luční enkláva Jelení louky a ve stejnojmenném hostinci s tradicí založenou již v roce 1731 se můžete občerstvit i dnes. Zpět můžete buď stejnou cestou nebo po asfaltce na Malou Pláň v Peci a zpět k autu. Cesta je vhodná i pro kočárek.
                        """.trimIndent(),
                introImage = getImg("jeleni-louky.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            )
        )

        return TripCategoryEntity(name = "Atrakce", trips_ids =  ids)
    }

    suspend fun car(): TripCategoryEntity {
        val ids = listOf(
            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Pevnost Stachelberg 20km",
                text = """
                        Pojedete asi 20 km autem, nejprve směrem dolů, před Trutnovem uhnete doleva na Žacléř, až se po stoupání dostanete nad obec Babí na parkoviště. Odtud je to asi 1 km pěšky k největší pevnosti z II sv. války v Čechách a jedné z největších v Evropě. Zejména pro táty a kluky je zde obrovská pevnost a řada dalších malých pevnůstek v okolí. Uvnitř je historická expozice s možností návštěvy podzemní části bunkru. Vedle pevnosti stojí malý bunkr zvaný “Řopík”, který je vybaven i zbraněmi. Podobné bunkry můžete nalézt nejen po celých Krkonoších ale i jinde v Čechách.
                        Tuto návštěvu můžete spojit buď s pěším výletem na Rýchorský prales, kde cestou uvidíte další velké bunkry nebo s návštěvou městského bazénu v Trutnově.
                        
                        Rodinné vstupné 			400Kč
                        """.trimIndent(),
                introImage = getImg("stach.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Adršpašsko-teplické skály 42 km",
                text = """
                        Pojedete autem do Trutnova, zde na 3 kr. objezdu doleva a dál směrem na Adršpach. V obci Chvaleč se dáte doleva. Aby jste zaparkovali u skal, je nutné si koupit  online vstupenky s rezervací parkování, o prázdninách doporučujeme min 2-3 dny předem. Jinak se vrátíte bez prohlídky.
                            Adršpašsko-teplické skály jsou největší a nejmonumentálnější skalní město v Čechách. Jeho návštěva, zejména mimo hlavní sezónu je opravdovým zážitkem pro malé i velké návštěvníky. Dětskou pozornost přitáhnou jak nejroztodivnější skály tak i možnost hrabat se a stavět z písku. Základní okruh v Adršpachu měří 3,5 km a trvá přibližně 3 hodiny. Návštěvu můžete zpestřit plavbou na lodičkách.
                        Pokud bude v Ádru plno, jeďte dále asi 3 km a zastavte u parkoviště před Teplickými skalami. Zde na Vás čeká základní okruh 6 km s monumentálními Chrámovými stěnami o výšce až 60m. Spojit můžete i oba okruhy.
                        Jestliže se nemůžete nabažit těchto pískovců, můžete dále navštívit další zajímavá místa: Broumovské stěny nebo Ostaš.
                        Zajímavé jsou i Jiráskovy skály se zbytky hradu na pískovcových věžích. Odtud je liduprázdný okruh přes rozhlednu Čáp a bludiště skal zpět k autu. S návštěvou hradu je 12 km.
                        
                        Vstupné variabilní, zhruba:		dospělí 180 Kč	rodinné 2+2	500 Kč
                        Parkování:	150 Kč

                        """.trimIndent(),
                introImage =getImg("adrspach3.jpg"),
                otherImages = listOf(getImg("adrspach2.jpg"),  getImg("adr.jpg")),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Safari park Dvůr Králové 41 km",
                text = """
                          Pojedete směrem na Hradec Králové a v obci Kocbeře uhnete doprava na Dvůr. Pojedete podle šipek až k ZOO a za vstupní bránou je nejbližší parkoviště. Jinak půjdete pěšky ke vstupu dost daleko.
	                      V Safari parku Vás čekají ty pravé africké zážitky – opravdové safari mezi volně se pohybujícími stády zvířat a jediné Lví safari ve střední Evropě. Pěší procházka vás poté zavede mezi největší skupinu žiraf chovaných mimo Afriku nebo k nosorožcům, jejichž chovem se dvorský safari park proslavil. Návštěvu si zejména s dětmi naplánujte na celý den a nebudete litovat.
                        """.trimIndent(),
                introImage = getImg("dvur.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Zámek Kuks 50km",
                text = """
                        Pojedete směrem na Hradec Králové a v obci Kuks uhnete doprava na parkoviště.
	                    Zámek Kuks je proslulý zejména svým exteriérem s velkolepými sochami Matyáše Brauna a nádhernou barokní lékárnou uvnitř. Zámek byl nedávno kompletně opraven a k prohlídce doporučujeme zejména okruh Historie lékáren.
                        Až se dostatečně pokocháte v zahradách zámku vraťte se do auta a jeďte směrem na obec Žíreč, poté doleva na Parkoviště Hřibojedy - Braunův betlém, celkem 5 km. Zde nechte zdarma auto a vydejte se po červené. minete několik krásných soch v lese až dorazíte k  Braunovu Betlému. (1km). Tato unikátní skalní kompozice skutečně stojí za návštěvu. Pro milovníky moderních soch pak doporučujeme pokračovat až na odbočku a po žluté po loukách, kde je umístěno asi 20 zajímavých moderních soch zpět přes kostelík a po silnici k autu (okruh celkem 5km).
                        Vstupné: 70 - 110 Kč podle okruhu
                        """.trimIndent(),
                introImage = getImg("kuks.jpeg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            )
        )

        return TripCategoryEntity(name = "Autem v okolí", trips_ids =  ids)
    }

    suspend fun turism(): TripCategoryEntity {
        val ids = listOf(
            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Sněžka z Velké Úpy 15 km",
                text = """
                       Navštívit vrchol Sněžky je cílem každého návštěvníka. Můžete ho dosáhnout buď lanovkou z Pece (link na stránku Sněžka lanovkou), ale více Vás bude těšit dojít na vrchol pěšky.
                        Od penzionu sejdete do centra, půjdete doleva za penzion Modřín, kde se napojíte na Stezku nosičů. Začnete stoupat lesíkem, pak po loukách a zase lesem vzhůru, Cestou minete tabule se zajímavými informacemi o historii nosičů. Napojíte se na silničku a po ní dál po žluté na Růžohorky, kde se můžete občerstvit. Pokračujte přes stanici lanovky až na vrchol pěšky. Zde se můžete kochat nádhernými výhledy na okolní hory, ale i nehoráznými cenami v kiosku. Doporučujeme, vemte si něco sami. Pak pokračujete po modré po kamenné silnici dolů na Obří sedlo, uhnete doleva a jdete úchvatným Obřím dolem. Občerstvení doporučujeme na Boudě pod Sněžkou, kde mají i rozptýlení pro děti - malou farmu. Dále po modré přes dolní stanici lanovky do Pece. Zde si sednete do první cukrárny a počkáte na první autobus domů
                        Kdo si chce cestu prodloužit o 5 km, tak vřele doporučujeme jít z Obřího sedla po hranici po červené až na vyhlídku na Malý staw - ledovcové jezero pod Sněžkou v Polsku, zpět na odbočku a dál po žluté na Luční boudu (místní pivovar a chutné koblihy) a přes Úpské rašeliniště zpět do Obřího sedla a standardní cestou dolů.
                        Cestu na Sněžku je možné jít i obráceně, zejména pro ty, které bolí kolena. Kombinovat ji můžete i s použitím lanovky. Pak je krajinově vděčné jít Obřím dolem nahoru nebo dolů.

                        """.trimIndent(),
                introImage = getImg("snezka.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Sněžka z Pomezních bud 17 km",
                text = """
                        Tato cesta je krajinově nejvděčnější pro návštěvu vrcholu Sněžky. Na Pomezní boudy se dostanete autobusem, o prázdninách 9:50 a 10:18. Vystoupíte a jdete dále směrem do Polska, až narazíte na rozcestník s odbočkou po modré směrem na Jelenku a Sněžku. Začnete stoupat, až se dostanete na rovné vrcholové plató končící nádhernou vyhlídkou Skalny stol. Sejdete dolů a za chvilce jste na boudě Jelenka, kde se můžete občerstvit místním pivem Trautenberk. Z Jelenky stoupáte dál po hřebeni přes Svorovou horu a několik vyhlídek na vrchol Sněžky. Zde se můžete kochat nádhernými výhledy na okolní hory, ale i nehoráznými cenami v kiosku. Doporučujeme, vemte si něco sami. Pak pokračujete po modré po kamenné silnici dolů na Obří sedlo, uhnete doleva a jdete úchvatným Obřím dolem. Občerstvení doporučujeme na Boudě pod Sněžkou, kde mají i rozptýlení pro děti - malou farmu. Dále po modré přes dolní stanici lanovky do Pece. Zde si sednete do první cukrárny a počkáte na první autobus domů.
                        """.trimIndent(),
                introImage = getImg("malaupa.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Černohorské rašeliniště a Vlašské boudy 7 km",
                text = """
                        Krajinově vděčný, nepříliš dlouhý výlet, vhodný i pro děti nebo kočárek s krásnými výhledy na rašeliniště nebo hřeben Sněžky. Pojedete autobusem v 8:57 směr Vrchlabí a vystoupíte v Janských lázních zastávka Lesní dům a  potom lanovkou na Černou horu. Odtud se vydáte po po žluté směrem na Černou boudu (jediné občerstvení) a na odbočce Václavák půjdete doprava. Vyšplháte na vyhlídkovou věž a prohlédnete si rašeliniště. Půjdete dále až k Hubertově vyhlídce, kde doporučujeme odbočit doprava a podívat se asi 200 na rašeliniště. Pokračujete stále po žluté s hezkými výhledy na masiv Sněžky až na Vlašské boudy, krásnou luční enklávu se zachovalými domky. Potom seběhnete okolo přezimovací obory pro jeleny přes Lučiny a vyjdete dole pod penzionem.
                        
                        Varianta 1: Kdo nechce použít lanovku, může jet jen do Temného dolu v Horním Maršově a pak po modré na Modrokamennou boudu. Odtud žlutá na Modré kameny, krásný vyhlídková skála, dále po zelené na Velké Pardubické boudy. Můžete si obejít rašeliniště a napojit se pak na žlutou domů. Celkem 13km
                        
                        Varianta 2: Pojedete autobusem až do Černého dolu nám. a odtud budete stoupat po žluté na Zrcadlovky a strmým svahem Černé hory k Sokolské boudě. Pak pokračujete dál po žluté domů. Před boudou doporučujeme zacházku asi 1 km na startovací rampu paraglidingu za níž jsou zajímavé “Švédské valy”. NIkdo neví kdo a kdy je někdo vytvořil. Celkem 11km
                        
                        """.trimIndent(),
                introImage = getImg("dvur.jpg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            ),

            tripDAO.insertTrip(
                hotelId =  hotelId,
                title = "Hřebenovka od Lyžařské boudy na Luční boudu 18 km",
                text = """
                        Tato cesta prochází nejvyšší partie Krkonoš s překrásnými výhledy. Autobusem nebo autem (parkoviště Zelený potok) do Pece. Odtud ke vlekům a lanovkou na Hnědý vrch. Zde navštívíte rozhlednu a potom pokračujete po cyklostezce na Lyžařskou boudu. Po delším stoupání po červené se dostanete na Liščí horu s krásnými výhledy na Sněžku. Dále pak Liščím hřebenem na bufet na Rozcestí. Kdo má dost, může sejít po zelené lesní cestou do Pece (celkem 11 km). Jinak pokračujte dál na Výrovku a stoupáním na Památník obětem hor. Odtud sejdete na Luční boudu, kde dáte zavděk místním pivem Paroháč. Z Luční dále již po modré přes povalový chodníček Úpského rašeliniště do Obřího sedla a Obřím dolem zpět do Pece. Kdo nechce sejít dolů, vystoupá Hřebínkem na Sněžku a sveze se dolů lanovkou.
                        Cestu je možné absolvovat i obráceně.
                        """.trimIndent(),
                introImage = getImg("kuks.jpeg"),
                otherImages = listOf(),
                tags = listOf("Hory", "Děti")
            )
        )

        return TripCategoryEntity(name = "Turistika", trips_ids =  ids)
    }






}