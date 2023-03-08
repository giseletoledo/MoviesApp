package com.ebac.moviesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebac.moviesapp.placeholder.PlaceholderContent
import com.ebac.moviesapp.placeholder.PlaceholderContent.PlaceholderItem

class MovieViewModel : ViewModel() {

    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData
    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    val movieListLiveData: LiveData<MutableList<PlaceholderItem>>
        get() = _movieListLiveData
    private val _movieListLiveData = MutableLiveData<MutableList<PlaceholderItem>>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    val dataStateLiveData: LiveData<DataState>
        get() = _dataStateLiveData
    private val _dataStateLiveData = MutableLiveData<DataState>()

    init {
        _movieListLiveData.postValue(PlaceholderContent.ITEMS)
    }

    fun onMovieSelected(position: Int) {

        _dataStateLiveData.postValue(DataState.LOADING) // atualiza o estado para Loading
        val movie = PlaceholderContent.ITEMS.getOrNull(position) // tenta obter o filme com base na posição
        if (movie == null) {
            _dataStateLiveData.postValue(DataState.ERROR) // atualiza o estado para Error se o filme não existir
        } else {
            val movieDetails = MovieDetails(
                "Wakanda Forever",
                "Em Pantera Negra: Wakanda Forever da Marvel Studios, a Rainha Ramonda (Angela Bassett), Shuri (Letitia Wright), M’Baku (Winston Duke), Okoye (Danai Gurira) e as Dora Milaje (incluindo Florence Kasumba), lutam para proteger sua nação contra as potências mundiais intervenientes logo após a morte do Rei T’Challa.")
            _movieDetailsLiveData.postValue(movieDetails)
            _dataStateLiveData.postValue(DataState.SUCCESS) // atualiza o estado para Success se o filme existir
            _navigationToDetailLiveData.postValue(Unit)
        }
    }
}