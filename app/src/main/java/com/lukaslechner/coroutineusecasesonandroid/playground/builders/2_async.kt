package com.lukaslechner.coroutineusecasesonandroid.playground.builders

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val start = time()

    val d1 = async {
        networkCall(1)
    }
    val d2 = async {
        networkCall(2)
    }
    val r1 = d1.await()
    val r2 = d2.await()

    println(r1 + r2)
    println("runBlocking " + elapsed(start))
}

private suspend fun networkCall(num: Int): String {
    delay(500)
    return "Hello $num!"
}

private fun elapsed(start: Long) = System.currentTimeMillis() - start
private fun time() = System.currentTimeMillis()