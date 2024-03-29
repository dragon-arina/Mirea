package com.mirea.laba2.ui.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Lazy
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity(open val layoutId: Int): AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    lateinit var root: View

    open val binding: Lazy<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        root = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(root)
        binding?.get()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestPermissionResultHandler?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private var requestPermissionResultHandler: RequestPermissionResultHandler? = null

    fun registerRequestPermissionResultHandler(handler: RequestPermissionResultHandler) {
        requestPermissionResultHandler = handler
    }

    interface RequestPermissionResultHandler {
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    }
}