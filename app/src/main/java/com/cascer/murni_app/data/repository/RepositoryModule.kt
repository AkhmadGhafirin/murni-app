package com.cascer.murni_app.data.repository

import com.cascer.murni_app.data.db.UserDao
import com.cascer.murni_app.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryImpl(
        userDao: UserDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): Repository {
        return RepositoryImpl(userDao, ioDispatcher)
    }
}