package com.kevinvi.doraibu.app.graphql.mapper

import android.util.Log
import com.kevinvi.common.TypeUi
import com.kevinvi.common.extension.empty
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.doraibu.AnimeQuery
import com.kevinvi.doraibu.app.graphql.model.CoverImage
import com.kevinvi.doraibu.app.graphql.model.Media
import com.kevinvi.doraibu.app.graphql.model.NextAiringEpisode
import com.kevinvi.doraibu.fragment.ItemFragment
import com.kevinvi.ui.model.FavItemUi

fun AnimeQuery.Page.toPageList(): List<Media> =
	this.media?.mapNotNull { media ->
		Media(
			id = media?.itemFragment?.id ?: 0,
			title = titleMapper(media?.itemFragment?.title),
			coverImage = media?.itemFragment?.coverImage?.let { coverMapper(it) } ?: CoverImage(String.empty, String.empty, String.empty),
			startDate = dateMapper(media?.itemFragment?.startDate),
			endDate = dateMapper(media?.itemFragment?.endDate),
			bannerImage = media?.itemFragment?.bannerImage ?: String.empty,
			season = media?.itemFragment?.season?.rawValue ?: String.empty,
			seasonYear = 0,
			description = media?.itemFragment?.description ?: String.empty,
			type = media?.itemFragment?.type?.rawValue ?: String.empty,
			format = media?.itemFragment?.format?.rawValue ?: String.empty,
			status = media?.itemFragment?.status?.rawValue ?: String.empty,
			episodes = media?.itemFragment?.episodes,
			genres = media?.itemFragment?.genres ?: emptyList(),
			nextAiringEpisode = media?.itemFragment?.nextAiringEpisode?.let { nextMapper(it) },
			relations = itemMapper(media?.relations?.edges?.map { it?.node?.itemFragment  }),
			recommendations  = itemMapper(media?.recommendations?.nodes?.map { it?.mediaRecommendation?.itemFragment }),

		)
	}.orEmpty()

fun itemMapper(items: List<ItemFragment?>?): List<Media> =
	items?.map { item ->
		Media(
			id = item?.id ?: 0,
			title = titleMapper(item?.title),
			coverImage = item?.coverImage?.let { coverMapper(it) } ?: CoverImage(String.empty, String.empty, String.empty),
			startDate = dateMapper(item?.startDate),
			endDate = dateMapper(item?.endDate),
			bannerImage = item?.bannerImage ?: String.empty,
			season = item?.season?.rawValue ?: String.empty,
			seasonYear = 0,
			description = item?.description ?: String.empty,
			type = item?.type?.rawValue ?: String.empty,
			format = item?.format?.rawValue ?: String.empty,
			status = item?.status?.rawValue ?: String.empty,
			episodes = item?.episodes,
			genres = item?.genres ?: emptyList(),
			nextAiringEpisode = item?.nextAiringEpisode?.let { nextMapper(it) },
			relations = null,
			recommendations = null
		)
	}.orEmpty()

fun titleMapper(titles: ItemFragment.Title?): List<String?> =
	listOf(titles?.userPreferred ?: String.empty, titles?.english ?: String.empty, titles?.native ?: String.empty, titles?.romaji ?: String.empty)

fun coverMapper(cover: ItemFragment.CoverImage): CoverImage =
	CoverImage(cover.extraLarge ?: String.empty, cover.large ?: String.empty, cover.color ?: String.empty)

fun dateMapper(date: ItemFragment.StartDate?): String = "${date?.day} / ${date?.month} / ${date?.year}"
fun dateMapper(date: ItemFragment.EndDate?): String = "${date?.day} / ${date?.month} / ${date?.year}"

fun nextMapper(air: ItemFragment.NextAiringEpisode): NextAiringEpisode = NextAiringEpisode(air.airingAt, air.timeUntilAiring, air.timeUntilAiring)

object MediaMapper {
	fun mapToDetail(animeItem: Media): FavItemUi {

		Log.d("MediaMapper", "mapToDetail: $animeItem")
		return FavItemUi(
			id = IdFavoriteUtils().buildId(animeItem.id.toString(), TypeUi.SCAN.name),
			type = TypeUi.ANIME2.name,
			title = animeItem.title[0],
			description = animeItem.description,
			lastEntry = getEpisode(animeItem.nextAiringEpisode, animeItem.episodes ?: 0),
			imageUrl = animeItem.coverImage.extraLarge,
			linkedAnimeRecommendations = animeItem.recommendations?.map { media -> mapToDetail(media) },
			linkedAnimeRelations = animeItem.relations?.map { media -> mapToDetail(media) }
		)
	}

	fun getEpisode(next: NextAiringEpisode?, episode: Int): Int {
		if (episode > 0) {
			return episode
		}
		if (next != null) {
			return next.episode
		}
		return 0
	}
}