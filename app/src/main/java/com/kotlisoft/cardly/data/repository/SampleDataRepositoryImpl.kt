package com.kotlisoft.cardly.data.repository

import com.kotlisoft.cardly.data.local.AssetDataSource
import com.kotlisoft.cardly.domain.repository.SampleDataRepository
import java.io.InputStream
import javax.inject.Inject

class SampleDataRepositoryImpl @Inject constructor(
    private val assetDataSource: AssetDataSource,
) : SampleDataRepository {
    override fun getSampleDataFromAssets(): InputStream {
        return assetDataSource.getSampleDataFromAssets()
    }
}