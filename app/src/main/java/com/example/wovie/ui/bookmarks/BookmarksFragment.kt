package com.example.wovie.ui.bookmarks

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wovie.databinding.FragmentBookmarksBinding
import com.example.wovie.ui.model.Film
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {
    private var _binding: FragmentBookmarksBinding? = null
    val binding: FragmentBookmarksBinding
        get() = _binding!!

    private val bookMarkViewModel: BookmarksViewModel by viewModels()
    private lateinit var bookMarksList: MutableList<Film>
    private lateinit var bookMarkAdapter: BookmarksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookMarksList = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val lm = StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL)

        bookMarkAdapter = BookmarksAdapter(bookMarksList, bookMarkViewModel, requireContext())
        binding.bookmarksRecyclerview.layoutManager = lm
        binding.bookmarksRecyclerview.adapter = bookMarkAdapter
        binding.deleteIcon.setOnClickListener { v ->
            val alertDialog =
                AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Please Confirm")
            alertDialog.setMessage("Are you sure you want to delete all of them?")
            alertDialog.setNegativeButton(
                "No"
            ) { dialog: DialogInterface?, pos: Int -> }
            alertDialog.setPositiveButton(
                "Yes"
            ) { dialog: DialogInterface?, pos: Int ->
                bookMarkViewModel.clearBookMarks()
                //bmObject.listener.onAllCleared(bookMarkResults!!.size)
            }
            alertDialog.create()
            alertDialog.show()
        }
        binding.backButton.setOnClickListener { closeFragment() }
        bookMarkViewModel.bookMarkResults.observe(viewLifecycleOwner) { results ->
            bookMarksList.clear()
            bookMarksList.addAll(results)
            bookMarkAdapter.notifyDataSetChanged()
            val flag = bookMarksList.size == 0
            binding.deleteIcon.isVisible = !flag
            binding.bookmarksRecyclerview.isVisible = !flag
            binding.noBookmarksLayout.isVisible = flag

        }
        bookMarkViewModel.msg.observe(viewLifecycleOwner, {
            if (it != null && !TextUtils.isEmpty(it)) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        bookMarkViewModel.loading.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it != null && it
        })
        return binding.root
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}