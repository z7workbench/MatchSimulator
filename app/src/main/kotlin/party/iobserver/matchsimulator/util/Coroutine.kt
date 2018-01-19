package party.iobserver.matchsimulator.util

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

object Coroutine {
    fun <T> coroutine(block: suspend CoroutineScope.() -> T, uiBlock: suspend (T) -> Unit): Deferred<T> {
        val deferred = async(CommonPool, false, block)
        launch(UI) {
            uiBlock(deferred.await())
        }
        return deferred
    }
}