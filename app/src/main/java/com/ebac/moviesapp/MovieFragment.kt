package com.ebac.moviesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.ebac.moviesapp.databinding.FragmentItemListBinding

class MovieFragment : Fragment(), MovieListener {

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

        val layoutManager = LinearLayoutManager(context)
        adapter = MyMovieRecyclerViewAdapter(this)

        binding.list.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = layoutManager
        }

        initObservers()

        return view
    }

  private fun initObservers(){
      val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
      val movieList = view?.findViewById<TextView>(R.id.list)
      val errorTextView = view?.findViewById<TextView>(R.id.errorTextView)

      viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
        adapter.updateData(it)
      })

      viewModel.dataStateLiveData.observe(viewLifecycleOwner, Observer { state ->
          progressBar?.let { progressBar ->
              movieList?.let { movieDetailsTextView ->
                  errorTextView?.let { errorTextView ->
                      when (state) {
                          DataState.LOADING -> {
                              progressBar.visibility = View.VISIBLE
                              movieDetailsTextView.visibility = View.GONE
                              errorTextView.visibility = View.GONE
                          }
                          DataState.ERROR -> {
                              progressBar.visibility = View.GONE
                              movieDetailsTextView.visibility = View.GONE
                              errorTextView.visibility = View.VISIBLE
                              errorTextView.text = "Falha ao carregar a lista"
                          }
                          DataState.SUCCESS -> {
                              progressBar.visibility = View.GONE
                              movieDetailsTextView.visibility = View.VISIBLE
                              errorTextView.visibility = View.GONE
                          }
                      }
                  }
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
        //findNavController().navigate(R.id.movieDetailsFragment)
    }
}