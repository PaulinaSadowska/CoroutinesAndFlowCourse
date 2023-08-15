package com.lukaslechner.coroutineusecasesonandroid.playground.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    coroutine(1, 500)
    coroutine(2, 300)

    joinAll(
        async { coroutine(3, 500) },
        async { coroutine(4, 300) }
    )
    println("main ends")
}

private suspend fun coroutine(number: Int, delay: Long) {
    println("Routine $number starts work!")
    delay(delay)
    println("Routine $number has finished")
}
