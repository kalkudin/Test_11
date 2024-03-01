package com.example.myapplication.domain.repository

import com.example.myapplication.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface CourseRepository{
    suspend fun getCourse() : Flow<Resource<Double>>
}