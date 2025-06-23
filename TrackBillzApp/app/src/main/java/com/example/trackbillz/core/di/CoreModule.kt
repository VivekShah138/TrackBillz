package com.example.trackbillz.core.di

import androidx.datastore.core.DataStore
import com.example.trackbillz.add_bills.data.data_source.DeleteAllBillsFromCloudDatabaseWorker
import com.example.trackbillz.add_bills.data.data_source.InsertAllBillsToLocalDatabaseWorker
import com.example.trackbillz.add_bills.data.data_source.UploadAllBillsToCloudDatabaseWorker
import com.example.trackbillz.core.data.data_source.userInfoDataStore
import com.example.trackbillz.core.data.data_source.userPreferencesDataStore
import com.example.trackbillz.core.data.repository.local.DataStoreRepositoryImpl
import com.example.trackbillz.core.data.repository.remote.FirebaseRemoteRepositoryImpl
import com.example.trackbillz.core.domain.model.UserInfo
import com.example.trackbillz.core.domain.model.UserPreferences
import com.example.trackbillz.core.domain.repository.local.DataStoreRepository
import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository
import com.example.trackbillz.core.domain.usecases.CoreUseCaseWrapper
import com.example.trackbillz.core.domain.usecases.local.GetUserPin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

import org.koin.core.qualifier.named

val USER_PREFS_DS = named("userPrefsDataStore")
val USER_INFO_DS = named("userInfoDataStore")

val coreModule = module {

    single<DataStore<UserPreferences>>(USER_PREFS_DS) {
        androidContext().userPreferencesDataStore
    }

    single<DataStore<UserInfo>>(USER_INFO_DS) {
        androidContext().userInfoDataStore
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<DataStoreRepository> {
        DataStoreRepositoryImpl(
            userPrefDataStore = get(USER_PREFS_DS),
            userInfoDataStore = get(USER_INFO_DS)
        )
    }

    single<FirebaseRemoteRepository> {
        FirebaseRemoteRepositoryImpl(
            firebaseAuth = get(),
            firestore = get(),
            context = androidContext()
        )
    }

    single<CoreUseCaseWrapper> {
        CoreUseCaseWrapper(
            getUserPin = GetUserPin(get())
        )
    }

    workerOf(::UploadAllBillsToCloudDatabaseWorker)
    workerOf(::DeleteAllBillsFromCloudDatabaseWorker)
    workerOf(::InsertAllBillsToLocalDatabaseWorker)
}