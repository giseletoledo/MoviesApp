package com.ebac.moviesapp

import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    fun loadMovieDetails() : MovieDetails{
        return MovieDetails("Wakanda Forever","Em Pantera Negra: Wakanda Forever da Marvel Studios, a Rainha Ramonda (Angela Bassett), Shuri (Letitia Wright), M’Baku (Winston Duke), Okoye (Danai Gurira) e as Dora Milaje (incluindo Florence Kasumba), lutam para proteger sua nação contra as potências mundiais intervenientes logo após a morte do Rei T’Challa.")
    }

}