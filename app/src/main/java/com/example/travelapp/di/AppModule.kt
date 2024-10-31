package com.example.travelapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.travelapp.data.repository.TravelRepository
import com.example.travelapp.data.repository.TravelRepositoryImpl
import com.example.travelapp.data.repository.UserRepository
import com.example.travelapp.data.repository.UserRepositoryImpl
import com.example.travelapp.data.source.local.LocalDataSource
import com.example.travelapp.data.source.local.LocalDataSourceImpl
import com.example.travelapp.data.source.local.preference.UserDataStore
import com.example.travelapp.data.source.local.preference.dataStore
import com.example.travelapp.data.source.local.room.TravelDao
import com.example.travelapp.data.source.local.room.TravelDatabase
import com.example.travelapp.data.source.remote.ApiService
import com.example.travelapp.data.source.remote.NetworkConfig
import com.example.travelapp.data.source.remote.RemoteDataSource
import com.example.travelapp.data.source.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService():ApiService{
        return NetworkConfig().getApiService()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService):RemoteDataSource{
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource):UserRepository{
        return UserRepositoryImpl(remoteDataSource,localDataSource)
    }

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideTravelRepository(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource): TravelRepository {
        return TravelRepositoryImpl(remoteDataSource,localDataSource)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(userDataStore: UserDataStore,travelDao: TravelDao): LocalDataSource {
        return LocalDataSourceImpl(userDataStore,travelDao)
    }

    @Provides
    @Singleton
    fun provideTravelDatabase(@ApplicationContext context: Context): TravelDatabase {
        return TravelDatabase.getInstance(context)

    }

    @Provides
    @Singleton
    fun provideTravelDao(travelDatabase: TravelDatabase) = travelDatabase.travelDao()


}