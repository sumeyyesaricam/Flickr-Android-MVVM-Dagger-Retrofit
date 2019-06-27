package com.appcent.app.data.remote

import com.appcent.app.data.model.Photos
import io.reactivex.Single
import retrofit2.http.*


interface ApiHelper {

    @Headers(("Accept: application/json"),("Content-type:application/json") )
    @GET("services/rest/?method=flickr.photos.getRecent&api_key=2176afe67ce94ea603ed291ed3c27068&extras=&format=json&nojsoncallback=1")
    fun getImageList(@Query("page") page: Int, @Query("per_page") pageSize: Int): Single<Photos>

}