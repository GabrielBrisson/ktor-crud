package com.brisson.plugins

import com.brisson.models.Customer
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

const val DATABASE = "MacGregorSoftware"
val client = KMongo.createClient().coroutine
val database = client.getDatabase(DATABASE)

val customerCollection = database.getCollection<Customer>()