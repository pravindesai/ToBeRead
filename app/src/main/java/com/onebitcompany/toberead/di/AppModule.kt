package com.onebitcompany.toberead.di

import android.app.Application
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.http.httpCache
import com.apollographql.apollo3.network.okHttpClient
import com.onebitcompany.toberead.common.Constants
import com.onebitcompany.toberead.data.repository.bookRepo.BookRepository
import com.onebitcompany.toberead.data.repository.bookRepo.BookRepositoryImpl
import com.onebitcompany.toberead.data.repository.genreRepo.GenreRepo
import com.onebitcompany.toberead.data.repository.genreRepo.GenreRepoImpl
import com.onebitcompany.toberead.data.repository.tagRepo.TagRepo
import com.onebitcompany.toberead.data.repository.tagRepo.TagRepoImpl
import com.onebitcompany.toberead.data.repository.userRepo.UserRepository
import com.onebitcompany.toberead.data.repository.userRepo.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        HttpLoggingInterceptor.Logger.DEFAULT
    ).apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(
                original.method,
                original.body
            )
            builder.addHeader(Constants.HEADER_KEY, Constants.HEADER_VAL)
            chain.proceed(builder.build())
        }.addNetworkInterceptor(httpLoggingInterceptor).build()

    @Provides
    @Singleton
    fun apolloCacheFile(application: Application): File =
        File(application.filesDir, Constants.CACHE_FILE_NAME)

    @Provides
    @Singleton
    fun providesApolloClient(httpClient: OkHttpClient, apolloCacheFile: File): ApolloClient =
        ApolloClient.Builder().httpCache(apolloCacheFile, Constants.CACHE_FILE_SIZE)
            .serverUrl(Constants.BASE_URL)
            .okHttpClient(httpClient).build()

    @Provides
    @Singleton
    fun providesUserRepository(apolloClient: ApolloClient): UserRepository = UserRepositoryImpl(apolloClient)

    @Provides
    @Singleton
    fun providesBookRepository(apolloClient: ApolloClient): BookRepository = BookRepositoryImpl(apolloClient)

    @Provides
    @Singleton
    fun providesTagRepository(apolloClient: ApolloClient): TagRepo = TagRepoImpl(apolloClient)

    @Provides
    @Singleton
    fun providesGenreRepository(apolloClient: ApolloClient): GenreRepo = GenreRepoImpl(apolloClient)

}