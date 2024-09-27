package com.example.modules.transactions.services

import com.example.modules.transactions.model.Transaction
import com.example.modules.transactions.model.TransactionResponse
import com.example.modules.transactions.model.TransactionStatus
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals

class ServiceTest {

    @Test
    fun voucherExecuteTest() {
        val foodTransactionMCC = Transaction("1234", 100f, "5412", "UBER")
        val foodTransactionMerchant = Transaction("1234", 100f, "5411", "Mercado")
        val foodTransactionMerchantNoBalance = Transaction("1234", 3000f, "5411", "Mercado")

        val mealTransactionMCC = Transaction("1234", 100f, "5811", "UBER")
        val mealTransactionMerchant = Transaction("1234", 100f, "5812", "IFOOD")
        val cashTransaction = Transaction("1234", 100f, "4100", "UBER")
        assertEquals(VoucherExecutor.execute(foodTransactionMCC), TransactionResponse(TransactionStatus.SUCCESS))
        assertEquals(VoucherExecutor.execute(foodTransactionMerchant), TransactionResponse(TransactionStatus.SUCCESS))
        assertEquals(VoucherExecutor.execute(foodTransactionMerchantNoBalance), TransactionResponse(TransactionStatus.FAILURE))
        assertEquals(VoucherExecutor.execute(mealTransactionMerchant), TransactionResponse(TransactionStatus.SUCCESS))
        assertEquals(VoucherExecutor.execute(mealTransactionMCC), TransactionResponse(TransactionStatus.SUCCESS))
        assertEquals(VoucherExecutor.execute(cashTransaction), TransactionResponse(TransactionStatus.SUCCESS))
    }
}