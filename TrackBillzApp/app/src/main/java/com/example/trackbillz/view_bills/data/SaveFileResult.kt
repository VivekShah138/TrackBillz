package com.example.trackbillz.view_bills.data

sealed class SaveFileResult{
    data class Success(val result: String): SaveFileResult()
    data class Failure(val result: String): SaveFileResult()
}
