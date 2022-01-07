package com.bankuish.challenge.presentation

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bankuish.challenge.databinding.FragmentProjectDetailBinding
import com.bankuish.challenge.domain.GitHubProject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ProjectListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ProjectDetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var project: GitHubProject? = null
    lateinit var itemDetailTextView: TextView
    private var _binding: FragmentProjectDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(PROJECT_ITEM)) {
                project = it.getParcelable(PROJECT_ITEM) as GitHubProject?
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProjectDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        itemDetailTextView = binding.itemDetail

        updateContent()

        return rootView
    }

    private fun updateContent() {
        binding.itemDetail.text = project?.description
        binding.detailToolbar.title = project?.name
        binding.detailToolbar.subtitle = project?.owner?.login
        // Show the placeholder content as text in a TextView.
//        item?.let {
//            itemDetailTextView.text = it.details
//        }
    }

    companion object {
        const val PROJECT_ITEM = "project_item"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}