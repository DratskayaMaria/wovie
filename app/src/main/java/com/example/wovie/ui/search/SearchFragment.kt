package com.example.wovie.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.FragmentSearchBinding
import com.example.wovie.ui.model.Film
import com.example.wovie.util.toFilm
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.OnSearchResultClickListener {
    private var _binding: FragmentSearchBinding? = null
    val binding: FragmentSearchBinding
        get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var foundFilms: ArrayList<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foundFilms = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchResults.layoutManager = lm
        val adapter = SearchAdapter(this, foundFilms)
        binding.searchResults.adapter = adapter
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        searchViewModel.searchResultMutableLiveData.observe(viewLifecycleOwner) { searchResult ->
            if (searchResult != null) {
                if (searchResult.results != null && searchResult.results!!.isEmpty()) {
                    binding.searchResults.visibility = View.GONE
                    binding.noResultsLayout.visibility = View.VISIBLE
                } else {
                    binding.searchResults.visibility = View.VISIBLE
                    binding.noResultsLayout.visibility = View.GONE
                }
                foundFilms.clear()
                searchResult.results?.map {
                    it.toFilm()
                }?.let { foundFilms.addAll(it) }
                adapter.notifyDataSetChanged()
            }
        }
        binding.backButton.setOnClickListener { v -> closeFragment() }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null && !TextUtils.isEmpty(query)) {
                    searchViewModel.getSearchResults(query, hasConnection())
                } else {
                    Toast.makeText(requireContext(), "Query Cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        searchViewModel.msg.observe(viewLifecycleOwner, {
            if (it != null && !TextUtils.isEmpty(it)) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        searchViewModel.loading.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it != null && it
        })
        return binding.root
    }

    private fun hasConnection(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }

    override fun onResultClick(film: Film) {
        val action: NavDirections =
            SearchFragmentDirections.actionSearchFragmentToFilmFragment(film)
        Navigation.findNavController(binding.root).navigate(action)
    }
}

