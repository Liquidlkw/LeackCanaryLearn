package com.konka.leackcanarylearn

import android.app.Application
import java.util.concurrent.Executor

class Test {
    fun main() {
        val runnable = Runnable { }


        test(checkRetainedExecutor = {

        })

        val objectSample = ObjectSample.invoke(Application())
        com.konka.leackcanarylearn.ObjectSample.test(111);
    }

    private fun test(checkRetainedExecutor: Executor) {
        //do nothing
    }
}