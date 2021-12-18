package com.lexcru.lexcruapp.network


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoadingShow: Boolean? = null,
    val id: Int? = null,
    val model: Any? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class SuccessWithData<T>(data: T, model: Any? = null) : Resource<T>(data = data, model = model)
    class Loading<T>(isLoadingShow: Boolean) : Resource<T>(isLoadingShow = isLoadingShow)
    class Error<T>(message: String) : Resource<T>(message = message)
    class ValidationError<T>(strId: Int) : Resource<T>(id = strId)
    class ValidationCheck<T>(isValidate: Boolean) : Resource<T>(isLoadingShow = isValidate)
    class NoInternetError1<T>(strId: Int) : Resource<T>(id = strId)
    class UnAuthorisedRequest<T>(message: String) : Resource<T>(message = message)

}