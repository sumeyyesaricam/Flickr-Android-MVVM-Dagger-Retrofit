package com.appcent.app.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.appcent.app.utils.CommonUtils
import dagger.android.AndroidInjection

abstract class BaseActivity<V : BaseViewModel<*>> : AppCompatActivity() {

    private var mProgressDialog: ProgressDialog? = null
    private var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
        isLoading()
    }

    private fun isLoading() {
        mViewModel?.getIsLoading()?.observe(this, Observer {
            it?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        })
    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    private fun performDataBinding() {
        setContentView(getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.cancel()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}