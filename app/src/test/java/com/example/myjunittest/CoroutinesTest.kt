package com.example.myjunittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CoroutinesTest {

    // test view model
    @get:Rule
    val instantExcutorRule = InstantTaskExecutorRule()

    // test coroutines
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val testScope = TestCoroutineScope()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test coroutines UI`() {
        runBlocking {
            launch(Dispatchers.Main) {
                (1..100).forEachIndexed { index, i ->
                    run {
//                        delay(500)
                        println(index)
                    }
                }
            }
        }
    }

}
