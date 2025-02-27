package com.droid.githubrepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droid.githubrepos.adapter.RepoAdapter
import com.droid.githubrepos.databinding.ActivityRepoListBinding
import com.droid.githubrepos.retrofit.GitHubRepository
import com.droid.githubrepos.retrofit.RetrofitClient
import com.droid.githubrepos.viewmodel.RepoViewModel
import com.droid.githubrepos.viewmodel.RepoViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoListBinding
    private lateinit var viewModel: RepoViewModel
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize dependencies
        val githubService = RetrofitClient.instance // Retrofit instance
        val repository = GitHubRepository(githubService) // GitHubRepository dependency

        // Create ViewModelFactory
        val viewModelFactory = RepoViewModelFactory(repository)

        // Obtain ViewModel using the factory
        viewModel = ViewModelProvider(this, viewModelFactory).get(RepoViewModel::class.java)

        setupRecyclerView()
        observeRepositories()
        observeErrors()

        // Fetch initial data
        viewModel.loadRepositories("google")
    }

    private fun setupRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Pagination: Load more data when the user scrolls to the bottom
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { // 1 means scrolling down
                    viewModel.loadRepositories("google") // Fetch the next page
                }
            }
        })
    }

    private fun observeRepositories() {
        viewModel.repositories.observe(this) { repositories ->
            adapter.updateRepositories(repositories) // Update the adapter with new data
        }
    }

    private fun observeErrors() {
        viewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show() // Show error message
        }
    }
}