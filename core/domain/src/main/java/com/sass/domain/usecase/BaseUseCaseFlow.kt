package com.sass.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCaseFlow<in Input, out Output>{
    abstract suspend operator fun invoke(input : Input) : Flow<Output>
}

abstract class BaseUseCase<in Input, out Output>{
    abstract suspend operator fun invoke(input : Input) : Output
}