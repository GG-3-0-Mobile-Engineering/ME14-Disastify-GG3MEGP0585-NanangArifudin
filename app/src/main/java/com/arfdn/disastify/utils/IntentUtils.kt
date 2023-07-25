package com.arfdn.disastify.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivityWithExtras(extras: Bundle) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(extras)
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Activity> Activity.startActivityForResultWithExtras(
    extras: Bundle,
    requestCode: Int
) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(extras)
    startActivityForResult(intent, requestCode)
}