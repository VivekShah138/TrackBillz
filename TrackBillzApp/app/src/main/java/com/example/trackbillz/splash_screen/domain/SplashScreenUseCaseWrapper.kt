package com.example.trackbillz.splash_screen.domain

import com.example.trackbillz.core.domain.usecases.local.GetUserId
import com.example.trackbillz.core.domain.usecases.remote.DeleteMultipleBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertMultipleBillsToCloud
import com.example.trackbillz.core.domain.usecases.remote.SyncRemoteBillsToLocal
import com.example.trackbillz.traders.domain.usecases.InsertAllPredefinedTraders

data class SplashScreenUseCaseWrapper(
    val getUserId: GetUserId,
    val insertAllPredefinedTraders: InsertAllPredefinedTraders
)
