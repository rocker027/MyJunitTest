package com.example.myjunittest.ui.register

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SurveyDslMockk {
    // use DSL mock
    val mother = mockk<Mother>()
    val kid = Kid(mother)
    /**
     * 只要小孩跟媽媽要錢，媽媽就給他 100 元
     */
    @Test
    fun wantMoney() {
        // give
        every { mother.giveMoney() } returns 30
        every { mother.inform(any()) } just Runs
        // when
        kid.wantMoney()
        // then
        verify { mother.inform(any()) }
        assertEquals(30, kid.money)
    }
}

class SurveyAnnotationMockk {
    @MockK
    lateinit var mother: Mother
    lateinit var kid: Kid

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        kid = Kid(mother)
    }

    /**
     * 只要小孩跟媽媽要錢，媽媽就給他 100 元
     */
    @Test
    fun wantMoney() {
        // give
        every { mother.giveMoney() } returns 30
        every { mother.inform(any()) } just Runs
        // when
        kid.wantMoney()
        // then
        verify { mother.inform(any()) }
        assertEquals(30, kid.money)
    }
}

class SurveyRelaxedMockk {
    // Relaxed
    //用途：該物件所有的方法都不需要指定

    // Dsl
//    val mother = mockk<Mother>(relaxed = true)
    @RelaxedMockK
    lateinit var mother: Mother

    //    RelaxUnitFun
//    用途：不需指定無回傳值的方法，有回傳值的方法仍須指定
//@MockK(relaxUnitFun = true)
//lateinit var mother: Mother
    lateinit var kid: Kid

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        kid = Kid(mother)
    }

    /**
     * 只要小孩跟媽媽要錢，媽媽就給他 100 元
     */
    @Test
    fun wantMoney() {
        //give
        every { mother.giveMoney() } returns 30
        //when
        kid.wantMoney()
        // then
        verify {
            mother.inform(any())
            mother.giveMoney()
        }
        assertEquals(30, kid.money)
    }
}

/**
 * 取得值
 */
class SurveyCaptureSlotMockk {
    @RelaxedMockK
    lateinit var mother: Mother

    lateinit var kid: Kid

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        kid = Kid(mother)
    }

    /**
     * 只要小孩跟媽媽要錢，媽媽就給他 100 元
     */
    @Test
    fun wantMoney() {
        //give
        val slot = slot<Int>()
        every { mother.inform(capture(slot)) } just Runs
        //when
        kid.wantMoney()
        // then
        assertEquals(0, slot.captured)
    }
}

class Kid(private val mother: Mother) {
    var money = 0
        private set

    fun wantMoney() {
        money += mother.giveMoney()
        mother.inform(money)
    }
}

class Mother {
    fun giveMoney(): Int {
        return 100
    }

    fun inform(money: Int) {
        println("小孩現在有$money ,要在跟媽媽拿錢")
    }
}