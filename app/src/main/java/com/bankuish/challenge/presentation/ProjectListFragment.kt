package com.bankuish.challenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bankuish.challenge.R
import com.bankuish.challenge.databinding.FragmentProjectListBinding
import com.bankuish.challenge.domain.GitHubProject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectListFragment : Fragment() {
    private val gitHubViewModel: GitHubProjectViewModel by viewModel()


    private var _binding: FragmentProjectListBinding? = null

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
        setupSwipeRefresh()
        updatePageContent()
    }

    private fun setupSwipeRefresh() {
        val swipeContainer = binding.swipeRefresh
        swipeContainer.setOnRefreshListener {
            requireActivity().runOnUiThread {
                gitHubViewModel.getKotlinRepos(true)
            }
        }
        swipeContainer.setColorSchemeResources(
            R.color.purple_200,
            R.color.purple_500,
            R.color.purple_700
        )

    }


    private fun updatePageContent() {
        gitHubViewModel.gitHubLiveData.observe(viewLifecycleOwner) { githubProjectUIState ->
            when (githubProjectUIState) {
                GitHubProjectViewModel.GithubProjectUIState.Loading -> showLoading()
                GitHubProjectViewModel.GithubProjectUIState.Error -> showError()
                is GitHubProjectViewModel.GithubProjectUIState.Success -> githubProjectUIState.projectList?.let {
                    showProjectList(
                        it
                    )
                }
            }
        }
        gitHubViewModel.getKotlinRepos(false)
    }

    private fun showProjectList(projectList: List<GitHubProject>) {
        binding.swipeRefresh.isRefreshing = false
        val recyclerView: RecyclerView = binding.itemList
        var adapter = recyclerView.adapter as? GitHubProjectAdapter
        if (recyclerView.adapter == null) {
            adapter = GitHubProjectAdapter()
        } else {
            adapter?.clear()
        }
        adapter?.addProjectList(projectList)
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