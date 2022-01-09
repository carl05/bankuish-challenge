package com.bankuish.challenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bankuish.challenge.databinding.FragmentProjectListBinding
import com.bankuish.challenge.domain.GitHubProject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectListFragment : Fragment() {
    // Lazy Inject ViewModel
    val gitHubViewModel: GitHubProjectViewModel by viewModel()


    private var _binding: FragmentProjectListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.itemList
        setupRecyclerView(recyclerView)
        val swipeContainer = binding.swipeRefresh
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            gitHubViewModel.getKotlinRepos()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    }



    private fun setupRecyclerView(
        recyclerView: RecyclerView
    ) {
        recyclerView.adapter = GitHubProjectAdapter()
        gitHubViewModel.gitHubLiveDate.observe(viewLifecycleOwner) { githubProjectUIState ->
            when (githubProjectUIState) {
                GitHubProjectViewModel.GithubProjectUIState.Loading -> showLoading()
                GitHubProjectViewModel.GithubProjectUIState.Error -> showError()
                is GitHubProjectViewModel.GithubProjectUIState.Success -> showProjectList(
                    githubProjectUIState.projectList
                )
            }
        }
        gitHubViewModel.getKotlinRepos()
    }

    private fun showProjectList(projectList: List<GitHubProject>?) {
        binding.swipeRefresh.isRefreshing = false
        val recyclerView: RecyclerView = binding.itemList
        val adapter = GitHubProjectAdapter()
        if (projectList != null) {
            adapter.addProjectList(projectList)
        }
        recyclerView.adapter = adapter
    }

    private fun showError() {
        binding.swipeRefresh.isRefreshing = false
        val viewSwitcher = binding.viewSwitcher
        if (viewSwitcher.currentView == binding.itemList) {
            viewSwitcher.showNext()
        }
    }

    private fun showLoading() {
        binding.swipeRefresh.isRefreshing = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}