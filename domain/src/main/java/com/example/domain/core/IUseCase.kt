package com.example.domain.core

interface IUseCase<in P, R> {
    fun execute(parameter: P): R
}