package com.kevinvi.doraibu.app.graphql.mapper

import com.kevinvi.common.extension.empty
import com.kevinvi.doraibu.AnimeQuery
import com.kevinvi.doraibu.app.graphql.model.AnimeModel
import com.kevinvi.doraibu.app.graphql.model.CoverImage
import com.kevinvi.doraibu.app.graphql.model.Media
import com.kevinvi.doraibu.app.graphql.model.NextAiringEpisode
import com.kevinvi.doraibu.type.MediaFormat
import com.kevinvi.doraibu.type.MediaSeason
import com.kevinvi.doraibu.type.MediaType

fun AnimeQuery.Page.toPageList(): List<Media> =
	this.media?.mapNotNull { madia ->
		Media(
			id = madia?.id ?: 0,
			title = titleMapper(madia?.title),
			coverImage = madia?.coverImage?.let { coverMapper(it) } ?: CoverImage(String.empty, String.empty, String.empty),
			startDate = dateMapper(madia?.startDate),
			endDate = dateMapper(madia?.endDate),
			bannerImage = madia?.bannerImage ?: String.empty,
			season = madia?.season?.rawValue ?: String.empty,
			seasonYear = 0,
			description = madia?.description ?: String.empty,
			type = madia?.type?.rawValue ?: String.empty,
			format = madia?.format?.rawValue ?: String.empty,
			status = madia?.status?.rawValue ?: String.empty,
			episodes = madia?.episodes,
			genres = madia?.genres ?: emptyList(),
			nextAiringEpisode = madia?.nextAiringEpisode?.let { nextMapper(it) },
		)
	}.orEmpty()

fun coverMapper(cover: AnimeQuery.CoverImage): CoverImage =
	CoverImage(cover.extraLarge ?: String.empty, cover.large ?: String.empty, cover.color ?: String.empty)

fun dateMapper(date: AnimeQuery.StartDate?): String = "${date?.day} / ${date?.month} / ${date?.year}"
fun dateMapper(date: AnimeQuery.EndDate?): String = "${date?.day} / ${date?.month} / ${date?.year}"

fun titleMapper(title: AnimeQuery.Title?): String = title?.userPreferred ?: String.empty

fun nextMapper(air: AnimeQuery.NextAiringEpisode): NextAiringEpisode = NextAiringEpisode(air.airingAt, air.timeUntilAiring, air.timeUntilAiring)
