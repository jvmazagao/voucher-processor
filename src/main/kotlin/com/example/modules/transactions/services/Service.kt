package com.example.modules.transactions.services

import com.example.modules.transactions.model.*
import io.ktor.server.plugins.*
import io.ktor.util.logging.*
import javax.naming.InsufficientResourcesException

val logger = KtorSimpleLogger("TransactionService")

fun interface WalletProcessor {
    fun debit(value: Float, type: Wallets)
}

val WalletProc = object : WalletProcessor  {
    val wallets: Map<Wallets, Wallet> = mapOf(
        Pair(Wallets.FOOD, FoodWallet(1000f)),
        Pair(Wallets.MEAL, MealWallet(1000f)),
        Pair(Wallets.CASH, CashWallet(1000f))
    )

    override fun debit(value: Float, type: Wallets) {
        val wallet = wallets[type] ?: throw NotFoundException()
        if (wallet.total - value < 0) {
            throw InsufficientResourcesException()
        }

        wallet.total -= value
    }
}

fun interface MerchantProcessor {
    fun accepts(merchant: String, acceptedMCCs: List<String>): Boolean
}

val MerchantProc = object: MerchantProcessor {
    val merchants: Map<String, String> = mapOf(
        Pair("UBER TRIP", "4000"),
        Pair("UBER EATS", "5812"),
        Pair("PAG*JoseDaSilva", "4000"),
        Pair("PICPAY*BILHETEUNICO", "4000"),
        Pair("SUPERMERCADOS VERDEMAR", "5411")
    )

    override fun accepts(merchant: String, acceptedMCCs: List<String>): Boolean {
        return acceptedMCCs.contains(merchants[merchant]);
    }
}

fun interface TransactionProcessor {
    fun process(transaction: Transaction,  wallet: WalletProcessor, cb: TransactionResponse): TransactionResponse
}


val FoodProcessor = object : TransactionProcessor {
    val tag = "FoodProcessor"
    val FOOD_MCC_LIST = listOf("5411", "5412")

    override fun process(transaction: Transaction, wallet: WalletProcessor,cb: TransactionResponse): TransactionResponse {
        val foodMerchantProcessor = MerchantProc

        try {
            if (foodMerchantProcessor.accepts(transaction.merchant.uppercase(), FOOD_MCC_LIST) or FOOD_MCC_LIST.contains(transaction.mcc)) {
                wallet.debit(transaction.totalAmount, Wallets.FOOD)
                return TransactionResponse(TransactionStatus.SUCCESS)
            }

        } catch (error: Exception) {
            logger.error(tag)
        }

        return cb
    }
}


val MealProcessor = object : TransactionProcessor {
    val tag = "MealProcessor"
    val MEAL_MCC_LIST = listOf("5811", "5812")
    override fun process(transaction: Transaction, wallet: WalletProcessor, cb: TransactionResponse): TransactionResponse {
        val mealMerchantProcessor = MerchantProc
        try {
            if (mealMerchantProcessor.accepts(transaction.merchant, MEAL_MCC_LIST) or MEAL_MCC_LIST.contains(transaction.mcc)) {
                wallet.debit(transaction.totalAmount, Wallets.MEAL)
                return TransactionResponse(TransactionStatus.SUCCESS)
            }
        } catch (error: Exception) {
            logger.error(tag)
        }

        return cb
    }
}



val CashProcessor = object: TransactionProcessor {
    val tag = "CashProcessor"
    override fun process(transaction: Transaction, wallet: WalletProcessor, cb: TransactionResponse): TransactionResponse {
        try {
            wallet.debit(transaction.totalAmount, Wallets.CASH)
            return TransactionResponse(TransactionStatus.SUCCESS)
        } catch (error: Exception) {
            logger.error(tag)
        }

        return cb
    }
}


fun interface Executor {
    fun execute(transaction: Transaction): TransactionResponse
}

val VoucherExecutor =
    Executor { transaction ->
        val wallet: WalletProcessor = WalletProc
        FoodProcessor.process(
            transaction, wallet, MealProcessor.process(
                transaction, wallet, CashProcessor.process(
                    transaction, wallet, TransactionResponse(TransactionStatus.FAILURE)
                )
            )
        )
    }