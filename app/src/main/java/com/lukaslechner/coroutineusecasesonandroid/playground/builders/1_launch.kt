package com.lukaslechner.coroutineusecasesonandroid.playground.builders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    GlobalScope.launch {
        delay(500)
        println("finished")
    }
    println("main ends")
}