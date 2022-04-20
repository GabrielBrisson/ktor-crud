package com.brisson.models

import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

@kotlinx.serialization.Serializable
data class Customer(
    @BsonId
    val id: String = UUID.randomUUID().toString().replace("-",""),
    val name: String,
    val lastName: String,
    val email: String
)

//public val customerStorage = mutableListOf<Customer>()
