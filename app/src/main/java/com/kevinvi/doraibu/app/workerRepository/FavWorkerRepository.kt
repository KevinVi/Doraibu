package com.kevinvi.doraibu.app.workerRepository

interface FavWorkerRepository {
	suspend fun fetchFavUpdate(): Pair<Boolean, List<Pair<String,Float>>>
}