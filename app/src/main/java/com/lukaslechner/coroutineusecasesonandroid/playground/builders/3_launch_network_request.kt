package com.lukaslechner.coroutineusecasesonandroid.playground.builders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            println("starts!")
            val result = networkRequest()
            delay(500)
            println(result)
        }
        delay(100)
        println("waiting...")
        job.start()
        println("still waiting...")
        job.join()
        println("end of endBlocking")
    }

    println("main ends")
}

private suspend fun networkRequest(): String {
    delay(500)
    return "hello!"
}