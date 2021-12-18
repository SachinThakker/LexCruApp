package com.lexcru.lexcruapp.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseFragment <V:BaseViewModel>:Fragment(){

    abstract val modelClass : KClass<V>
    lateinit var viewModel : V

    abstract fun getLayout():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        addObserver()
    }

    open fun addObserver(){}
    open fun removeObserver(){}
    protected abstract fun init()
    protected abstract fun setListeners()

    private fun createViewModel(savedInstanceState: Bundle?) {
        this.viewModel = getViewModel(clazz = modelClass)
        this.viewModel.readFrom(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.writeTo(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObserver()
    }

    private var progressDialog: ProgressDialog? = null

    fun showProgressDialog(
        mContext: Context,
        title: String = "",
    ) {
        progressDialog = ProgressDialog(mContext)
        progressDialog?.setCancelable(false)
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            } else {
                it.show()
            }
        }
    }

    fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}