package com.appcent.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appcent.app.data.DataManager
import com.appcent.app.ui.image_list.ImageListViewModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewModelProviderFactory @Inject constructor(private val dataManager: DataManager): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ImageListViewModel::class.java)){
            return ImageListViewModel(dataManager) as T
        }
        return super.create(modelClass)
    }
}