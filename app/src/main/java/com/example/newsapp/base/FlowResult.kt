package com.example.newsapp.base

import com.example.newsapp.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okhttp3.ResponseBody
import retrofit2.Response

//
//sealed interface Result<out T> {
//    data class Success<T>(val data: T) : Result<T>
//
//    data class Error(
//        val exception: Throwable? = null,
//        val responseBody: ResponseBody? = null,
//        val errorMessageRes: Int = R.string.http_status_unknown_developer_message,
//    ) : Result<Nothing>
//
//    data object Loading : Result<Nothing>
//}
//
//fun <T : Any> Flow<T>.asResult(): Flow<RequestResult<T>> {
//    return this
//        .map<T, RequestResult<T>> { RequestResult.Success(it) }
//        .onStart { emit(RequestResult.InProgress()) }
//        .catch { emit(RequestResult.Error(it)) }
//}
//
//suspend fun <T> Flow<T>.collectAsResult(
//    onSuccess: suspend (T) -> Unit = {},
//    onError: suspend (ex: Throwable, exRes: Int) -> Unit = { _, _ -> },
//    onLoading: () -> Unit = {},
//) {
//    asResult().collect { result ->
//        when (result) {
//            is RequestResult.Success -> RequestResult.Success(mapper(data))
//            is RequestResult.Error -> RequestResult.Error(data?.let(mapper))
//            is RequestResult.InProgress -> RequestResult.InProgress(data?.let(mapper))
//            is RequestResult.Success -> onSuccess(result.data)
//            is Result.Error -> {
//                val errorMessageRes = when (result.exception) {
//                    else -> R.string.http_status_unknown_developer_message
//                }
//                onError(
//                    result.exception ?: UnknownError(),
//                    errorMessageRes,
//                )
//            }
//
//            Result.Loading -> onLoading()
//        }
//    }
//}