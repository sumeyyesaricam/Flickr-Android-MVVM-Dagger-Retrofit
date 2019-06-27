package com.appcent.app.data

import com.appcent.app.data.model.Photos
import com.appcent.app.data.remote.ApiHelper
import io.reactivex.Single
import javax.inject.Inject

class AppDataManager @Inject constructor(
    private val apiHelper: ApiHelper
) : DataManager {

    override fun getImageList(page: Int, pageSize: Int): Single<Photos> = apiHelper.getImageList(page, pageSize)

}