package com.droid.githubrepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droid.githubrepos.adapter.RepoAdapter
import com.droid.githubrepos.databinding.ActivityRepoListBinding
import com.droid.githubrepos.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListActivity : AppCompatActivity() {

    // ViewBinding for the activity
    private lateinit var binding: ActivityRepoListBinding

    // ViewModel instance
    private val viewModel: RepoViewModel by viewModels()

    // Adapter for the RecyclerView
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        setupRecyclerView()

        // Observe repository data from ViewModel
        observeRepositories()

        // Observe error messages from ViewModel
        observeErrors()

        // Fetch initial data
        viewModel.fetchRepositories("google") // Replace with dynamic input if needed
    }

    private fun setupRecyclerView() {
        // Initialize the adapter
        adapter = RepoAdapter(emptyList())

        // Set up RecyclerView with a LinearLayoutManager and the adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Add pagination: Load more data when the user scrolls to the bottom
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { // 1 means scrolling down
                    viewModel.fetchRepositories("google") // Fetch the next page
                }
            }
        })
    }

    private fun observeRepositories() {
        // Observe the list of repositories from the ViewModel
        viewModel.repositories.observe(this) { repositories ->
            adapter.updateRepositories(repositories) // Update the adapter with new data
        }
    }

    private fun observeErrors() {
        // Observe error messages from the ViewModel
        viewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show() // Show error message
        }
    }
}