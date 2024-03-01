package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.common.Resource
import com.example.myapplication.data.mapper.mapToDomain
import com.example.myapplication.data.service.CurrencyService
import com.example.myapplication.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val currencyService: CurrencyService
) : CourseRepository {
    override suspend fun getCourse(): Flow<Resource<Double>> {
        return handleResponse.safeApiCall { currencyService.getCurrentRate() }
            .mapToDomain { courseDto ->
                courseDto.course
            }
    }
}