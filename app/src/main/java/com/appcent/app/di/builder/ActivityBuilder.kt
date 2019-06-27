package com.appcent.app.di.builder

import com.appcent.app.di.scope.ActivityScope
import com.appcent.app.ui.image_detail.ImageDetailActivity
import com.appcent.app.ui.image_list.ImageListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun bindImageListActivity(): ImageListActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun bindImageDetailActivity(): ImageDetailActivity

}