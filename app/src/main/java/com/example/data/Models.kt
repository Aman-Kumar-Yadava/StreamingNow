package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbConfigurationResponse(
    @Json(name = "images") val images: ImagesConfig? = null
)

@JsonClass(generateAdapter = true)
data class ImagesConfig(
    @Json(name = "base_url") val baseUrl: String,
    @Json(name = "secure_base_url") val secureBaseUrl: String,
    @Json(name = "poster_sizes") val posterSizes: List<String>
)

@JsonClass(generateAdapter = true)
data class TmdbPageResponse<T>(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<T>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class MediaItem(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String? = null,
    @Json(name = "name") val name: String? = null, // TV shows use name
    @Json(name = "poster_path") val posterPath: String? = null,
    @Json(name = "backdrop_path") val backdropPath: String? = null,
    @Json(name = "vote_average") val voteAverage: Double? = null,
    @Json(name = "release_date") val releaseDate: String? = null,
    @Json(name = "first_air_date") val firstAirDate: String? = null
) {
    val displayTitle: String get() = title ?: name ?: "Unknown"
    val displayYear: String get() = (releaseDate ?: firstAirDate ?: "").take(4)
}
