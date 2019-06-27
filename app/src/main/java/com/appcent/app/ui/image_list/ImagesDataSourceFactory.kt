package com.appcent.app.ui.image_list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.appcent.app.data.model.Photo
import com.appcent.app.data.remote.ApiHelper
import com.appcent.app.data.remote.ImageDataSource

class ImagesDataSourceFactory(private val apiHelper: ApiHelper) : DataSource.Factory<Int, Photo>()  {

    val imagesDataSourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, Photo> {
        val imagesDataSource = ImageDataSource(apiHelper)
        imagesDataSourceLiveData.postValue(imagesDataSource)
        return imagesDataSource
    }
}