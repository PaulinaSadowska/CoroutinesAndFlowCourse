package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts ${Thread.currentThread()}")

    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) },
        async {
            repeat(5) {
                println("working... ${Thread.currentThread()}")
                delay(100)
            }
        }
    )
    println("main ends")
}

private suspend fun coroutine(number: Int, delay: Long) {
    println("Routine $number starts work! ${Thread.currentThread()}")
    delay(delay)
    println("Routine $number has finished")
}