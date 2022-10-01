package com.daylantern.tengokberita.di

import android.content.Context
import android.content.SharedPreferences
import com.daylantern.tengokberita.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

}