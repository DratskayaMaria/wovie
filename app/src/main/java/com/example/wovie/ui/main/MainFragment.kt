package com.example.wovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wovie.databinding.FragmentMainBinding
import com.example.wovie.ui.model.Film
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var nowPlayingAdapter: MainAdapter
    private lateinit var popularAdapter: MainAdapter
    private lateinit var topRatedAdapter: MainAdapter
    private lateinit var upcomingAdapter: MainAdapter

    private lateinit var nowPlayingList: MutableList<Film>
    private lateinit var popularList: MutableList<Film>
    private lateinit var topRatedList: MutableList<Film>
    private lateinit var upcomingList: MutableList<Film>

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlayingList = mutableListOf()
        popularList = mutableListOf()
        topRatedList = mutableListOf()
        upcomingList = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initRootView()
        setUpObservers()
        return binding.root
    }

    private fun initRootView() {
        binding.nowPlayingRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        nowPlayingAdapter = MainAdapter(nowPlayingList, viewModel, requireContext())
        popularAdapter = MainAdapter(popularList, viewModel, requireContext())
        topRatedAdapter = MainAdapter(topRatedList, viewModel, requireContext())
        upcomingAdapter = MainAdapter(upcomingList, viewModel, requireContext())

        binding.nowPlayingRecyclerview.adapter = nowPlayingAdapter
        binding.popularRecyclerview.adapter = popularAdapter
        binding.topRatedRecyclerview.adapter = topRatedAdapter
        binding.upcomingRecyclerview.adapter = upcomingAdapter

        binding.nowPlayingRecyclerview.isNestedScrollingEnabled = false
        binding.popularRecyclerview.isNestedScrollingEnabled = false
        binding.topRatedRecyclerview.isNestedScrollingEnabled = false
        binding.upcomingRecyclerview.isNestedScrollingEnabled = false
    }

    private fun setUpObservers() {
        binding.searchView.setOnClickListener { v ->
            val action: NavDirections = MainFragmentDirections.actionHomeFragmentToSearchFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        viewModel.nowPlayingMutableLiveData.observe(viewLifecycleOwner) { films ->
            nowPlayingList.clear()
            nowPlayingList.addAll(films)
            nowPlayingAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = nowPlayingList.size != 0
            binding.noResultsLayout.isVisible = nowPlayingList.size == 0
        }
        viewModel.popularMutableLiveData.observe(viewLifecycleOwner) { films ->
            popularList.clear()
            popularList.addAll(films)
            popularAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = popularList.size != 0
            binding.noResultsLayout.isVisible = popularList.size == 0
        }
        binding.bookMarks.setOnClickListener {
            val action: NavDirections = MainFragmentDirections.actionHomeFragmentToBookMarksFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.retryButton.setOnClickListener { viewModel.getFilms() }
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it != null && it
        })
        viewModel.msg.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}