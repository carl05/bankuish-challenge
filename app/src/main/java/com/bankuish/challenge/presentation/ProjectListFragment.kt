package com.bankuish.challenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bankuish.challenge.data.Item
import com.bankuish.challenge.databinding.FragmentProjectListBinding
import com.bankuish.challenge.databinding.ProjectListContentBinding
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
        var list = ArrayList<Item>()
        gitHubViewModel.projectList.value?.let {
            list.addAll(it.items)
        }
        recyclerView.adapter = SimpleItemRecyclerViewAdapter()
        gitHubViewModel.projectList.observe(viewLifecycleOwner, {
            (recyclerView.adapter as? SimpleItemRecyclerViewAdapter)?.addProjectList(it.items)
        })

        gitHubViewModel.getKotlinRepos()
    }


    class SimpleItemRecyclerViewAdapter(
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        var projectList = mutableListOf<Item>()
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
            holder.authosTextView.text = item.owner.login
//            with(holder.itemView) {
//                tag = item
//                setOnClickListener {}

//                setOnLongClickListener { v ->
//                    // Setting the item id as the clip data so that the drop target is able to
//                    // identify the id of the content
//                    val clipItem = ClipData.Item(item.name)
//                    val dragData = ClipData(
//                        v.tag as? CharSequence,
//                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
//                        clipItem
//                    )
//
//                    v.startDragAndDrop(
//                        dragData,
//                        View.DragShadowBuilder(v),
//                        null,
//                        0
//                    )
//                }
//            }
        }

        fun addProjectList(itens: List<Item>) {
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