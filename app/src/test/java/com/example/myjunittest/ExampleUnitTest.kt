package com.example.myjunittest

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRxjavaDemo() {
        val testObserver = TestObserver<String>()
        Single.just("1")
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.assertResult("1")
    }

    @Test
    fun testCoroutines() {
        CoroutineScope(Dispatchers.Default).launch{
            println("test")
        }
    }
}
