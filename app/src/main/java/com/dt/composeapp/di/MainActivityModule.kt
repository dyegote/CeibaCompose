package com.dt.composeapp.di

import android.content.Context
import com.dt.composeapp.database.repository.PersonRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun providePersonRepository(@ApplicationContext context: Context) : PersonRespository = PersonRespository(context)
}