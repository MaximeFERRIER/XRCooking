package fr.droidfactory.xrcooking.domain.models

sealed class ResultState<out T> {
    data object Uninitialized : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class Failure(val exception: Throwable) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
}