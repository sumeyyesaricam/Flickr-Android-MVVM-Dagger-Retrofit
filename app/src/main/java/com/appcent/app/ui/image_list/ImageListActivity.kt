package com.appcent.app.ui.image_list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcent.app.BR
import com.appcent.app.R
import com.appcent.app.databinding.ActivityImageListBinding
import com.appcent.app.di.scope.ActivityScope
import com.appcent.app.ui.base.BaseActivity
import com.appcent.app.utils.State
import com.appcent.app.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_image_list.*
import kotlinx.android.synthetic.main.item_list_footer.*
import kotlinx.android.synthetic.main.item_list_footer.progress_bar
import kotlinx.android.synthetic.main.item_list_footer.txt_error
import javax.inject.Inject

@ActivityScope
class ImageListActivity : BaseActivity<ImageListViewModel>(), ImageListNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var mImageListViewModel: ImageListViewModel
    lateinit var mImageAdapter: ImageAdapter

    override fun getLayoutId(): Int = R.layout.activity_image_list

    override fun getViewModel(): ImageListViewModel {
        mImageListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImageListViewModel::class.java)
        return mImageListViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImageListViewModel.setNavigator(this)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        recyclerImage.layoutManager = GridLayoutManager(this, 2)
        recyclerImage.itemAnimator = DefaultItemAnimator()
        mImageAdapter = ImageAdapter { mImageListViewModel.retry() }
        recyclerImage.adapter = mImageAdapter
        mImageListViewModel.imageListLiveData.observe(this, Observer {
            mImageAdapter.submitList(it)
        })
    }

    private fun initState() {
        mImageListViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (mImageListViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            if (mImageListViewModel.listIsEmpty() && state == State.ERROR)
                mImageListViewModel.retry()
            if (!mImageListViewModel.listIsEmpty()) {
                mImageAdapter.setState(state ?: State.DONE)
            }
        })
    }

}
