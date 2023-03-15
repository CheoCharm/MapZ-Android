package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ItemWriteGroupBinding
import com.cheocharm.presentation.model.GroupMemberModel
import com.cheocharm.presentation.model.GroupModel

class GroupsAdapter(private val onClick: (GroupModel) -> Unit) :
    ListAdapter<GroupModel, GroupsAdapter.ViewHolder>(GroupDiffCallback) {

    class ViewHolder(private val binding: ItemWriteGroupBinding, val onClick: (GroupModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                binding.group?.let { group ->
                    onClick(group)
                }
            }
        }

        fun bind(groupModel: GroupModel) {
            with(binding) {
                group = groupModel

                val numberOfMembersExceedingFour = groupModel.numberOfMembers - 4
                val members: List<GroupMemberModel>

                if (numberOfMembersExceedingFour <= 0) {
                    members = groupModel.members
                    tvWriteNumberOfGroupMembers.isVisible = false
                } else {
                    members = groupModel.members.subList(0, 4)
                    tvWriteNumberOfGroupMembers.text = String.format(
                        binding.root.context.resources.getString(
                            R.string.format_write_number_of_group_members
                        ), numberOfMembersExceedingFour
                    )
                    tvWriteNumberOfGroupMembers.isVisible = true
                }

                rvWriteGroupMembers.adapter = MembersAdapter().apply {
                    submitList(members)
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWriteGroupBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position).run {
            viewHolder.bind(this)
        }
    }
}

object GroupDiffCallback : DiffUtil.ItemCallback<GroupModel>() {
    override fun areItemsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: GroupModel, newItem: GroupModel): Boolean =
        oldItem.name == newItem.name && oldItem.groupImageUrl == newItem.groupImageUrl
}
