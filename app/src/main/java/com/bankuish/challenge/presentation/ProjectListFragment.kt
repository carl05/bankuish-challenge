package com.bankuish.challenge.presentation

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bankuish.challenge.R
import com.bankuish.challenge.databinding.FragmentProjectListBinding
import com.bankuish.challenge.databinding.ProjectListContentBinding
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
        binding.progressBarCyclic.visibility = View.GONE
        val recyclerView: RecyclerView = binding.itemList
        val adapter = GitHubProjectAdapter()
        if (projectList != null) {
            adapter.addProjectList(projectList)
        }
        recyclerView.adapter = adapter
    }

    private fun showError() {
        binding.progressBarCyclic.visibility = View.GONE
        val viewSwitcher = binding.viewSwitcher
        if (viewSwitcher.currentView == binding.itemList) {
            viewSwitcher.showNext()
        }
    }

    private fun showLoading() {
        binding.progressBarCyclic.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}