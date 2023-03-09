package com.ebac.moviesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.ebac.moviesapp.databinding.FragmentItemListBinding

class MovieFragment : Fragment(), MovieListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var movieList: RecyclerView
    private lateinit var errorTextView: TextView


    private lateinit var adapter: MyMovieRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentItemListBinding.inflate(
            inflater
        )
        val view = binding.root

        progressBar = binding.progressBarList
        movieList = binding.list
        errorTextView = binding.errorTextView


        val layoutManager = LinearLayoutManager(context)
        adapter = MyMovieRecyclerViewAdapter(this)

        movieList.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = layoutManager
        }

        initObservers(progressBar, movieList, errorTextView)

        return view
    }

  private fun initObservers(progressBar: ProgressBar,
                            movieList: RecyclerView,
                            errorTextView: TextView){

      viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
        adapter.updateData(it)
      })

      viewModel.dataStateLiveData.observe(viewLifecycleOwner, Observer { state ->
          when (state) {
              DataState.LOADING -> {
                  progressBar.visibility = View.VISIBLE
                  movieList.visibility = View.GONE
                  errorTextView.visibility = View.GONE
              }
              DataState.ERROR -> {
                  progressBar.visibility = View.GONE
                  movieList.visibility = View.GONE
                  errorTextView.visibility = View.VISIBLE
                  errorTextView.text = "Falha ao carregar a lista"
              }
              DataState.SUCCESS -> {
                  progressBar.visibility = View.GONE
                  movieList.visibility = View.VISIBLE
                  errorTextView.visibility = View.GONE
              }
          }
      })

      viewModel.navigationToDetailLiveData.observe(viewLifecycleOwner, Observer {
          val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailsFragment()
          findNavController().navigate(action)
      })
  }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }
}