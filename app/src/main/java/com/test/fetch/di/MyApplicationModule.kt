package com.test.fetch.di

import android.content.Context
import com.test.fetch.R
import com.test.fetch.data.services.ListServices
import com.test.fetch.presentation.activities.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyApplicationModule {

    @Provides
    fun providesMainApplicationInstance(@ApplicationContext context: Context): MyApplication {
        return context as MyApplication
    }


    @Provides
    @Singleton
    fun provideBookService(retrofit: Retrofit): ListServices {
        return retrofit.create(ListServices::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}