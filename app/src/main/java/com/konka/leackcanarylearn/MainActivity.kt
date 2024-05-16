package com.konka.leackcanarylearn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    var leak: Leak? = Leak()
    val refQueue = ReferenceQueue<Any>()
    val retainedList = mutableListOf<WeakReference<Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content: TextView = findViewById(R.id.content)
        //对象在被回收之前会进入引用队列
        val weakReference = WeakReference<Any>(leak, refQueue)
        retainedList.add(weakReference)
        val setNullButton: Button = findViewById(R.id.button1)
        val gcButton: Button = findViewById(R.id.button2)
        setNullButton.setOnClickListener {
            leak = null
            content.append("leak = null")
            content.append("\n")
        }

        gcButton.setOnClickListener {
            System.gc()
            Thread.sleep(100)
            //what happen?
            System.runFinalization()

            removeWeaklyReachableObjects()
            if (retainedList.isEmpty()) {
                content.append("WeakReference object collected " + weakReference.get())
            } else {
                content.append("WeakReference object still exists " + weakReference.get())
            }
            content.append("\n")
        }

        // onlyGC()
    }

    private fun removeWeaklyReachableObjects() {
        // WeakReferences are enqueued as soon as the object to which they point to becomes weakly
        // reachable. This is before finalization or garbage collection has actually happened.
        var ref: WeakReference<Any>?
        do {
            ref = refQueue.poll() as WeakReference<Any>?
            if (ref != null) {
                retainedList.removeAt(0)
            }
        } while (ref != null)
    }

    fun onlyGC() {
        var o1: Any? = Object()
        val w1: WeakReference<Any> = WeakReference<Any>(o1)

        println(o1)
        println(w1.get())

        o1 = null
        System.gc()
        println(o1.toString());
        println(w1.get())
    }
}