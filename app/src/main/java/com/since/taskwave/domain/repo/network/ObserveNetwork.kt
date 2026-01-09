package com.since.taskwave.domain.repo.network

import kotlinx.coroutines.flow.Flow

interface ObserveNetwork {

    val observeNetwork : Flow<Boolean>

}