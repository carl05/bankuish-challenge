package com.bankuish.challenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bankuish.challenge.R
import com.bankuish.challenge.databinding.ProjectListContentBinding
import com.bankuish.challenge.domain.GitHubProject

class GitHubProjectAdapter : RecyclerView.Adapter<GitHubProjectAdapter.ViewHolder>() {
    var projectList = mutableListOf<GitHubProject>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ProjectListContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = projectList[position]
        holder.nameTextView.text = item.name
        holder.authosTextView.text = item.owner?.login
        val onClickListener = View.OnClickListener { itemView ->
            val bundle = Bundle()
            bundle.putParcelable(
                ProjectDetailFragment.PROJECT_ITEM,
                projectList[position]
            )
            itemView.findNavController().navigate(R.id.show_item_detail, bundle)
        }
        holder.itemView.setOnClickListener(onClickListener)
    }

    fun addProjectList(itens: List<GitHubProject>) {
        this.projectList = itens.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = projectList.size

    inner class ViewHolder(binding: ProjectListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTextView: TextView = binding.txtNameValue
        val authosTextView: TextView = binding.txtAuthorValue
    }

}
