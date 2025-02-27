package com.droid.githubrepos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droid.githubrepos.R
import com.droid.githubrepos.model.Repository

class RepoAdapter(
    private var repositories: List<Repository>
) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.repoName)
        val repoDescription: TextView = itemView.findViewById(R.id.repoDescription)
        val repoLanguage: TextView = itemView.findViewById(R.id.repoLanguage)
        val repoStars: TextView = itemView.findViewById(R.id.repoStars)
        val repoForks: TextView = itemView.findViewById(R.id.repoForks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repositories[position]

        holder.repoName.text = repo.name
        holder.repoDescription.text = repo.description ?: "No description"
        holder.repoLanguage.text = repo.language ?: "Unknown"
        holder.repoStars.text = "Stars: ${repo.stargazers_count}"
        holder.repoForks.text = "Forks: ${repo.forks_count}"
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return repositories.size
    }

    // Update the list of repositories and notify the adapter of data changes
    fun updateRepositories(newRepositories: List<Repository>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }
}