package com.kotlisoft.cardly.domain.repository

import java.io.InputStream

interface SampleDataRepository {
    fun getSampleDataFromAssets(): InputStream
}