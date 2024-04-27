package com.kevinvi.doraibu.app.graphql.model

import kotlinx.serialization.SerialName

data class AnimeModel(
	val data: Data,
)

data class Data(
	@SerialName("Page") val page: Page,
)

data class Page(
	val media: List<Media>,
)

data class Media(
	val id: String,
	val title: Title,
	val coverImage: CoverImage,
	val startDate: Date,
	val endDate: Date,
	val bannerImage: String,
	val season: String,
	val seasonYear: Int,
	val description: String,
	val type: String,
	val format: String,
	val status: String,
	val episodes: Int?,
	val genres: List<String>,
	val nextAiringEpisode: NextAiringEpisode?,
)

data class Title(
	val userPreferred: String,
)

data class CoverImage(
	val extraLarge: String,
	val large: String,
	val color: String,
)

data class Date(
	val year: String,
	val month: String,
	val day: String,
)

data class NextAiringEpisode(
	val airingAt: Int,
	val timeUntilAiring: Int,
	val episode: Int,
)
