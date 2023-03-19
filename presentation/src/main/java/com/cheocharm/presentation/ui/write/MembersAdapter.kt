package com.cheocharm.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cheocharm.domain.model.GroupMember
import com.cheocharm.presentation.databinding.ItemGroupMemberBinding

class MembersAdapter : ListAdapter<GroupMember, MembersAdapter.ViewHolder>(MemberDiffCallback) {

    class ViewHolder(private val binding: ItemGroupMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: GroupMember) {
            binding.member = member
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGroupMemberBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position).run {
            viewHolder.bind(this)
        }
    }
}

object MemberDiffCallback : DiffUtil.ItemCallback<GroupMember>() {
    override fun areItemsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: GroupMember, newItem: GroupMember): Boolean =
        oldItem.imageUrl == newItem.imageUrl
}
