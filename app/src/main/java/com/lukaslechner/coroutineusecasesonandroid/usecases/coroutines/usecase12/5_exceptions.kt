package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val handler = CoroutineExceptionHandler { context, throwable ->
        println("exception happened! $throwable")
    }

    val scope = CoroutineScope(SupervisorJob() + handler)

    val job = scope.launch {
        scope.launch {
            println("Child 1 starts")
            delay(50)
            println("Child 1 fails")
            throw RuntimeException("error!")
        }

        scope.launch {
            println("Child 2 starts")
            delay(500)
            println("Child 2 has completed")
        }.invokeOnCompletion { cause: Throwable? ->
            if (cause is CancellationException) {
                println("Child 2 was cancelled")
            }
        }
    }

    Thread.sleep(1_000)

    println(scope.isActive)
}