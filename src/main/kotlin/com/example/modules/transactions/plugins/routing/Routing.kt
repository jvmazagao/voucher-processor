package com.example.modules.transactions.plugins.routing

import com.example.modules.transactions.model.Transaction
import com.example.modules.transactions.model.TransactionResponse
import com.example.modules.transactions.model.TransactionStatus
import com.example.modules.transactions.services.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureTransactionRouting() {
    routing {
        route("/transactions") {
            post {
                val transaction = call.receive<Transaction>()
                val response = VoucherExecutor.execute(transaction)
                call.respond(response);
            }
        }

    }
}
