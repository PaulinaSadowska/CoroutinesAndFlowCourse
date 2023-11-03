package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            val result = calculateFactorialOf(factorialOf)
            val startConversion = System.currentTimeMillis()
            val resultString = withContext(Dispatchers.Default) {
                result.toString()
            }
            val now = System.currentTimeMillis()
            uiState.value = UiState.Success(
                resultString,
                computationDuration = startConversion - start,
                stringConversionDuration = now - startConversion
            )
        }
    }

    private suspend fun calculateFactorialOf(value: Int): BigInteger {
        return withContext(Dispatchers.Default) {
            (1..value)
                .fold(BigInteger.ONE) { acc: BigInteger, i: Int ->
                    acc.multiply(BigInteger.valueOf(i.toLong()))
                }
        }
    }
}