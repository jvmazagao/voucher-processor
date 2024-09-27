package com.example.modules.transactions.model

abstract class Wallet(open var total: Float) {
}

data class FoodWallet(override var total: Float): Wallet(total)
data class MealWallet(override  var total: Float): Wallet(total)
data class CashWallet(override  var total: Float): Wallet(total)

enum class Wallets {
    FOOD,
    MEAL,
    CASH
}