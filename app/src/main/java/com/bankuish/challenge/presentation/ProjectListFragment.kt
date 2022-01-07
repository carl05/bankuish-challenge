package com.bankuish.challenge.presentation

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

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

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
        val list = ArrayList<GitHubProject>()
        gitHubViewModel.projectList.value?.let {
            list.addAll(it)
        }
        recyclerView.adapter = SimpleItemRecyclerViewAdapter()
        gitHubViewModel.projectList.observe(viewLifecycleOwner, {
            (recyclerView.adapter as? SimpleItemRecyclerViewAdapter)?.addProjectList(it)
        })

        gitHubViewModel.getKotlinRepos()
    }


    class SimpleItemRecyclerViewAdapter(
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}