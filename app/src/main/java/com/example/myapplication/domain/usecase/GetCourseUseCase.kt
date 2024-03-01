package com.example.myapplication.domain.usecase

import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(private val courseRepository: CourseRepository) {
    suspend operator fun invoke() : Flow<Resource<Double>> {
        return courseRepository.getCourse()
    }
}