package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.SampleDataRepository
import java.io.InputStream
import javax.inject.Inject

class DataImportUseCase @Inject constructor(
    private val repository: SampleDataRepository,
) {
    operator fun invoke(): InputStream {
        return repository.getSampleDataFromAssets()
    }
}