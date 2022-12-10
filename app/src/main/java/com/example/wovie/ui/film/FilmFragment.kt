package com.example.wovie.ui.film

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wovie.databinding.FragmentFilmBinding
import com.example.wovie.ui.main.MainAdapter
import com.example.wovie.ui.model.Actor
import com.example.wovie.ui.model.Film
import com.example.wovie.util.loadImage
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class FilmFragment : Fragment() {
    private var _binding: FragmentFilmBinding? = null
    val binding: FragmentFilmBinding
        get() = _binding!!

    private val filmViewModel: FilmViewModel by viewModels()

    private lateinit var actorsAdapter: ActorAdapter
    private lateinit var recommendedAdapter: FilmAdapter

    private var actorsList: MutableList<Actor> = mutableListOf()
    private var recommendedList: MutableList<Film> = mutableListOf()

    private lateinit var film: Film
    var genres: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        genres = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        film = FilmFragmentArgs.fromBundle(requireArguments()).result
        bindView()
        return binding.root
    }

    private fun bindView() {
        binding.actorsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        actorsAdapter = ActorAdapter(actorsList)
        recommendedAdapter = FilmAdapter(recommendedList)

        binding.actorsRecycler.adapter = actorsAdapter
        binding.recommendedRecycler.adapter = recommendedAdapter

        filmViewModel.getGenresList()
        filmViewModel.getCast(film.filmId)
        filmViewModel.getRecommendedList(film.filmId)

        filmViewModel.genres.observe(viewLifecycleOwner) { responseGenres ->
            film.genres?.map { filmGenreId ->
                val genre = responseGenres?.first { it.id == filmGenreId }?.name!!
                genre
            }?.let { genres!!.addAll(it) }
            val adapter = genres?.let { GenreAdapter(it) }
            binding.generesRecyclerview.adapter = adapter
            film.cover?.let { loadImage(requireContext(), it, binding.coverImage) }
            film.poster?.let { loadImage(requireContext(), it, binding.posterImage) }
            binding.rating.text = film.rating.toString()
            binding.topTitle.text = film.title
            binding.overview.text = film.description
            binding.voters.text = film.countFeedbacks.toString()
            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            binding.generesRecyclerview.layoutManager = layoutManager
            binding.backButton.setOnClickListener { closeFragment() }
            binding.filmContainer.visibility = View.VISIBLE
        }
        filmViewModel.actors.observe(viewLifecycleOwner) { actors ->
            actorsList.clear()
            actorsList.addAll(actors)
            Log.d(this::class.java.name, "actorsList: $actorsList")
            actorsAdapter.notifyDataSetChanged()
            binding.actorsRecycler.isVisible = actorsList.size != 0
            binding.actorsTitle.isVisible = actorsList.size != 0
        }
        filmViewModel.recommended.observe(viewLifecycleOwner) { films ->
            recommendedList.clear()
            recommendedList.addAll(films)
            Log.d(this::class.java.name, "recommended: $recommendedList")
            recommendedAdapter.notifyDataSetChanged()
            binding.recommendedRecycler.isVisible = recommendedList.size != 0
            binding.recommendedTitle.isVisible = recommendedList.size != 0
        }
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}