package com.kevinvi.doraibu.app.workerRepository

import android.util.Log
import com.kevinvi.anime.data.model.AnimeEpisodesItem
import com.kevinvi.anime.data.repository.AnimeRepository
import com.kevinvi.anime.mapper.AnimeItemMapper
import com.kevinvi.common.TypeUi
import com.kevinvi.common.extension.empty
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.ui.model.FavItemUi
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FavWorkerRepositoryImpl @Inject constructor(
	val favRepository: FavRepository,
	val scanRepository: ScanRepository,
	val animeRepository: AnimeRepository,
) : FavWorkerRepository {
	override suspend fun fetchFavUpdate(): Pair<Boolean, List<Pair<String, Float>>> {

		val list: ArrayList<Pair<String, Float>> = arrayListOf()
		var isUpdated = false
		favRepository.getAll().firstOrNull()?.forEach { favItem ->
			val id = IdFavoriteUtils().getIdFromFavorite(favItem.id)
			when (favItem.type) {
				TypeUi.SCAN.name -> {
					//call scan
					if (!favItem.isFinished) {
						scanRepository.getLastestChapter(id).let {
							val scan = ScanItemMapper.mapToDetail(it)
							Log.d("TAG", "getDetail:notification Scan ${scan.title} ${scan.lastEntry}")
							Log.d("TAG", "getDetail:notification Scan cache ${favItem.lastEntry}")
							if (scan.lastEntry > favItem.lastEntry) {

								Log.d("TAG", "fetchFavUpdate notification : ${scan.title}  && ${(scan.lastEntry - favItem.lastEntry)}")
								list.add(Pair(favItem.title ?: String.empty, (scan.lastEntry - favItem.lastEntry).toFloat()))
								favItem.createdAt = scan.createdAt
								favItem.updatedAt = scan.updatedAt
								favItem.lastEntry = scan.lastEntry
								favItem.linked = scan.linked
								favRepository.save(favItem)
								isUpdated = true
							}
						}
					}
				}

				TypeUi.ANIME.name -> {
					//call anime
					animeRepository.getAnimeEpisodes(id).let { it ->
						if (it.pagination.hastNextPage) {
							setupLastAnimeEpisode(favItem, animeRepository.getAnimeEpisodesLast(id, it.pagination.lastVisiblePage.toString())).let { pair ->
								if (pair != null) {
									list.add(pair)
									favRepository.save(favItem)
									isUpdated = true
								}
							}
						} else {
							setupLastAnimeEpisode(favItem, it).let { pair ->
								if (pair != null) {
									list.add(pair)
									favRepository.save(favItem)
									isUpdated = true
								}
							}
						}
					}
				}

			}
		}
		return isUpdated to list.toList()
	}

	private fun setupLastAnimeEpisode(favItem: FavItemUi, episodes: AnimeEpisodesItem): Pair<String, Float>? {
		val anime = AnimeItemMapper.mapToEpisodeDetail(episodes)

		Log.d("TAG", "getDetail: notification ${anime.lastEntry}   ${favItem.lastEntry}")
		return if (anime.lastEntry > favItem.lastEntry) {
			val pair = Pair(favItem.title ?: String.empty, (anime.lastEntry - favItem.lastEntry).toFloat())
			favItem.lastEntry = anime.lastEntry
			return pair
		} else {
			null
		}

	}
}