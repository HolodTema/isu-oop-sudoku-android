package com.terabyte.sudokucppgame.core.data.di

import com.terabyte.sudokucppgame.core.data.jni.SudokuNative
import com.terabyte.sudokucppgame.core.data.jni.SudokuNativeImpl
import com.terabyte.sudokucppgame.core.data.repository.SudokuRepositoryImpl
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSudokuNative(): SudokuNative {
        return SudokuNativeImpl()
    }

    @Provides
    @Singleton
    fun provideSSudokuRepository(impl: SudokuRepositoryImpl): SudokuRepository {
        return impl;
    }
}
