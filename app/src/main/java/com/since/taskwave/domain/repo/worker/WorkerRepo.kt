package com.since.taskwave.domain.repo.worker

interface WorkerRepo {

    suspend fun oneTimeWorker()

    suspend fun periodicTimeWorker()
}