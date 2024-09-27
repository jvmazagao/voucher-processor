package com.example.modules.transactions.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(val account: String, val totalAmount: Float, val mcc: String, val merchant: String)

enum class TransactionStatus(val status: String) {
    SUCCESS("00"),
    FAILURE("51"),
    UNAVAILABLE("07")
}

@Serializable
data class TransactionResponse(val code: TransactionStatus)