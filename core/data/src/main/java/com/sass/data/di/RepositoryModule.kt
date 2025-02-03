package com.sass.data.di

import com.sass.data.repositoryImp.CurrencyRepositoryImp
import com.sass.data.repositoryImp.DataSourceRepositoryImp
import com.sass.domain.repository.CurrencyRepository
import com.sass.domain.repository.DataSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCurrencyRepository(currencyRepositoryImp: CurrencyRepositoryImp): CurrencyRepository

    @Singleton
    @Binds
    abstract fun bindDataSourceRepository(dataSourceRepository: DataSourceRepositoryImp): DataSourceRepository
}
