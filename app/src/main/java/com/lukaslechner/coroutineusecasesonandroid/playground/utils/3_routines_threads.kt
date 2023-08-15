package com.lukaslechner.coroutineusecasesonandroid.playground.utils

import kotlin.concurrent.thread

fun main() {
    println("main starts")
    threadRoutine(1, 500)
    threadRoutine(2, 300)
    Thread.sleep(1_000)
    println("main ends")
}

private fun threadRoutine(number: Int, delay: Long) {
    thread {
        println("Routine $number starts work! ${Thread.currentThread()}")
        Thread.sleep(delay)
        println("Routine $number has finished")
    }
}
