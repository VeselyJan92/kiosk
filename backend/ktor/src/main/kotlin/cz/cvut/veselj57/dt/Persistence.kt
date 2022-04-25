package cz.cvut.veselj57.dt

import org.litote.kmongo.KMongo


interface Config{
    val DATABASE_NAME: String // "bt"
    val PORT: Int // 27017
    val IP: String  //"localhost"

    fun getURL(ip: String, port: Int) = "mongodb://$ip:$port"

    fun getMongoDatabase() = KMongo.createClient(getURL(IP, PORT)).getDatabase(DATABASE_NAME)
}
