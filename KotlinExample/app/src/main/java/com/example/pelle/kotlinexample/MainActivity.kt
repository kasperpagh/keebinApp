package com.example.pelle.kotlinexample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

fun Context.toast(message: CharSequence){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
fun AppCompatActivity.pellesSjoveFunc(text: CharSequence){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}
class MainActivity internal constructor() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("penis")
        pellesSjoveFunc("hej med dig")
    }

}
