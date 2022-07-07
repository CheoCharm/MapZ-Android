package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.domain.Group
import com.cheocharm.domain.GroupMember
import com.cheocharm.presentation.R
import com.cheocharm.presentation.databinding.ItemWriteGroupBinding

class GroupsAdapter(private val onClick: (Group) -> Unit) :
    ListAdapter<Group, GroupsAdapter.ViewHolder>(GroupDiffCallback) {

    class ViewHolder(private val binding: ItemWriteGroupBinding, val onClick: (Group) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentGroup: Group? = null

        init {
            itemView.setOnClickListener {
                currentGroup?.let {
                    onClick(it)
                }
            }
        }

        fun bind(group: Group) {
            currentGroup = group

            with(binding) {
                // TODO: 그룹 이미지 설정
                tvWriteGroupName.text = group.name

                val numberOfMembersExceedingFour = group.numberOfMembers
                val members: List<GroupMember>

                if (numberOfMembersExceedingFour == 0) {
                    members = group.members
                    tvWriteNumberOfGroupMembers.isVisible = false
                } else {
                    members = group.members.subList(0, 4)
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

object GroupDiffCallback : DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean =
        oldItem.name == newItem.name && oldItem.groupImageUrl == newItem.groupImageUrl
}
