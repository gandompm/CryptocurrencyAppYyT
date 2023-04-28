package com.plcoding.cryptocurrencyappyt.di

import androidx.compose.ui.unit.Constraints
import com.plcoding.cryptocurrencyappyt.commen.Constants
import com.plcoding.cryptocurrencyappyt.data.remote.CoinPaprikaApi
import com.plcoding.cryptocurrencyappyt.data.repository.CoinRepositoryImpl
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * the whole purpose of dependency injection is to make our dependency replaceable
 *
 * dependency will be like an object
 *
 * we want to avoid hardcoding dependencies into our objects
 *
 * we inject the dependency from the outside, then we can easily swap dependency
 * if we want to use the fake repo, or the implementation
 */

/**
 * we tell dagger hill
 * this is how you can create dependencies
 */

@Module
// it will live as long as the application
@InstallIn(SingletonComponent::class)
object AppModule {

    // providing dependencies
    @Provides
    // it's make sure, that there is only a single instance
    @Singleton
    fun providePaprikaApi() : CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }


    @Provides
    @Singleton
    fun provideCoinRepositoryImpl(api: CoinPaprikaApi) : CoinRepository{
        return CoinRepositoryImpl(api)
    }
}