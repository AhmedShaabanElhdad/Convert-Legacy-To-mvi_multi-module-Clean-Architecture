package com.example.domain.core

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): ViewState<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    ViewState.Success(it)
                }
            }
        } catch (e: Exception) {
            Log.d("Exception:- ",e.message?:"")
            ViewState.Error(e.message?:"")
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
