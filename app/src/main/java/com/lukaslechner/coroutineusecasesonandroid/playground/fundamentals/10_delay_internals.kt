package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts ${Thread.currentThread()}")
    joinAll(
        async { coroutine(3, 500) },
        async { coroutine(4, 300) }
    )
    println("main ends")
}

private fun coroutine(number: Int, delay: Long) {
    println("Routine $number starts work! ${Thread.currentThread()}")
    Handler(Looper.getMainLooper())
        .postDelayed({
            println("Routine $number has finished")
        }, delay)

}

/*
suspend functions:
- perform some long running and can be suspended
- can be called from another suspend function or a coroutine
 */