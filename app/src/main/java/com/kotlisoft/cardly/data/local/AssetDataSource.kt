package com.kotlisoft.cardly.data.local

import android.content.Context
import java.io.InputStream

class AssetDataSource(private val context: Context) {
    fun getSampleDataFromAssets(): InputStream {
        return context.assets.open("spanish.csv")
    }
}