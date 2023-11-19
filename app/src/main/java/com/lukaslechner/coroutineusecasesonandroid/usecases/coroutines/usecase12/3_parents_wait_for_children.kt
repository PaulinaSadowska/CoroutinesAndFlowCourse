package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking() {

    val scope = CoroutineScope(Dispatchers.Default)

    val job = scope.launch {
        launch {
            delay(1000)
            println("Child 1 has completed")
        }

        launch {
            delay(1000)
            println("Child 2 has completed")
        }
    }

   // scope.coroutineContext[Job]!!.cancelAndJoin()
   job.join()

    println("completed")
}