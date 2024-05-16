package com.konka.leackcanarylearn

import android.app.Application

object ObjectSample : (Application) -> Unit {
    override fun invoke(p1: Application) {
        TODO("Not yet implemented")
    }

    fun test(a: Int) {
        println(a)
    }
}