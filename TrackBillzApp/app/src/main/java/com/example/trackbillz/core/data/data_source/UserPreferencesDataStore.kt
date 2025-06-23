package com.example.trackbillz.core.data.data_source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.trackbillz.core.data.utils.Crypto
import com.example.trackbillz.core.domain.model.UserInfo
import com.example.trackbillz.core.domain.model.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64


val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserPreferencesSerializer
)

val Context.userInfoDataStore : DataStore<UserInfo> by dataStore(
    fileName = "user_info.pb",
    serializer = UserInfoSerializer
)



