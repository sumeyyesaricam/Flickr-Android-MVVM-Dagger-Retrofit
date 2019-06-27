package com.appcent.app.ui.image_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.appcent.app.data.DataManager
import com.appcent.app.data.model.Photo
import com.appcent.app.data.remote.ImageDataSource
import com.appcent.app.ui.base.BaseViewModel
import com.appcent.app.utils.State
import javax.inject.Inject

class ImageListViewModel @Inject constructor(private val dataManager: DataManager) :
    BaseViewModel<ImageListNavigator>() {

    var imageListLiveData: LiveData<PagedList<Photo>>

    private val pageSize = 20
    private val imagesDataSourceFactory: ImagesDataSourceFactory

    init {
        imagesDataSourceFactory = ImagesDataSourceFactory(dataManager)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        imageListLiveData = LivePagedListBuilder<Int, Photo>(imagesDataSourceFactory, config).build()

    }


    fun getState(): LiveData<State> = Transformations.switchMap<ImageDataSource,
            State>(imagesDataSourceFactory.imagesDataSourceLiveData, ImageDataSource::state)

    fun retry() {
        imagesDataSourceFactory.imagesDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return imageListLiveData.value?.isEmpty() ?: true
    }
}