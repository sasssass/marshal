package com.sass.marshal.db

import com.sass.data.di.RepositoryModule
import com.sass.domain.repository.CurrencyRepository
import com.sass.domain.repository.DataSourceRepository
import com.sass.marshal.db.fakeRepo.FakeCurrencyRepository
import com.sass.marshal.db.fakeRepo.FakeDataSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class FakeRepositoryModule {
    @Binds
    abstract fun bindCurrencyRepository(currencyRepository: FakeCurrencyRepository): CurrencyRepository

    @Singleton
    @Binds
    abstract fun bindDataSourceRepository(dataSourceRepository: FakeDataSourceRepository): DataSourceRepository
}
