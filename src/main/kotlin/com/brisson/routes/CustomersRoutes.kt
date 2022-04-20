package com.brisson.routes

import com.brisson.models.Customer
import com.brisson.plugins.customerCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.eq

fun Route.customersRouting() {

    route("/customer") {
        //Find all customers
        get {
            val customerList = customerCollection.find().toList()
            call.respond(customerList)
        }
        //Find a specific customer
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customerCollection.findOne(Customer::id eq id) ?: return@get call.respondText(
                    text = "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        // Create a customer
        post {
            val customer = call.receive<Customer>()
            customerCollection.insertOne(customer)
            val response = customerCollection.findOne(Customer::email eq customer.email)
            call.respond(response!!)
        }

        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            if (customerCollection.deleteOne(Customer::id eq id).wasAcknowledged()) {
                call.respondText(
                    text = "Customer with id $id was deleted",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    text = "Could not delete",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}