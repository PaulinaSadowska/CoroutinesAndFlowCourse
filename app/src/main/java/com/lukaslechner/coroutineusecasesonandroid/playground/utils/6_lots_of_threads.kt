package com.lukaslechner.coroutineusecasesonandroid.playground.utils

import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun main() {
    repeat(1_000_000) {
        thread {
            sleep(5_000)
            print(".")
        }
    }
}