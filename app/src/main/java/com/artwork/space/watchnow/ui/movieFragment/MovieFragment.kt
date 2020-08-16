package com.artwork.space.watchnow.ui.movieFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artwork.space.watchnow.R
import com.artwork.space.watchnow.utils.EspressoIdlingResource
import com.artwork.space.watchnow.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {

            val movieAdapter = MovieAdapter()

            val factory = ViewModelFactory.getInstance(requireActivity().application)
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            EspressoIdlingResource.increment()
            movie_fragment_progress_bar.visibility = View.VISIBLE
            viewModel.getAllMovies().observe(viewLifecycleOwner, Observer { movies ->
                movie_fragment_progress_bar.visibility = View.INVISIBLE
                movieAdapter.setMovies(movies)
                movieAdapter.notifyDataSetChanged()
                EspressoIdlingResource.decrement()
            })

            with(main_movies_recycler_view) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}