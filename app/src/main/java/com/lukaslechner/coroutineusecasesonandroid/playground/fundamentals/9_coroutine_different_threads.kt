package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    println("main starts ${Thread.currentThread()}")

    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) },
    )
    println("main ends")
}

private suspend fun coroutine(number: Int, delay: Long) {
    println("Routine $number starts work! ${Thread.currentThread()}")
    delay(delay)
    withContext(Dispatchers.Default) {
        println("Routine $number has finished  ${Thread.currentThread()}")
    }
}