package com.alejandrorios.art_catalog_app.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// Im not sure if this should be separated in Data/Domain so I will leave this here,
// as Google suggests "Don't hardcode Dispatchers when creating new coroutines or calling withContext"
// so this implementation serves to inject the dispatcher
interface AppDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

class AppDispatchersImpl : AppDispatchers {
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}
