package com.arfdn.disastify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.bindFragment(bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return binding ?: createBinding(thisRef.layoutInflater, thisRef.view?.parent as? ViewGroup?, bindingInflater).also { binding = it }
        }
    }
}

private fun <T : ViewBinding> createBinding(inflater: LayoutInflater, container: ViewGroup?, bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T): T {
    return bindingInflater.invoke(inflater, container, false)
}

fun <T : ViewBinding> AppCompatActivity.bindActivity(bindingInflater: (LayoutInflater) -> T): ReadOnlyProperty<AppCompatActivity, T> {
    return object : ReadOnlyProperty<AppCompatActivity, T> {
        private var binding: T? = null

        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            return binding ?: createBinding(thisRef.layoutInflater, bindingInflater).also { binding = it }
        }
    }
}

private fun <T : ViewBinding> createBinding(inflater: LayoutInflater, bindingInflater: (LayoutInflater) -> T): T {
    return bindingInflater.invoke(inflater)
}