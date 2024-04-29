package com.alejandrorios.art_catalog_app.utils

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

interface MockKableTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
