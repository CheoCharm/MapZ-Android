package com.cheocharm.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.cheocharm.domain.model.Group
import com.cheocharm.presentation.databinding.ItemWriteGroupBinding
import com.cheocharm.presentation.ui.write.GroupDiffCallback
import com.cheocharm.presentation.ui.write.GroupsAdapter

class GroupsPagingAdapter(private val onClick: (Group) -> Unit) :
    PagingDataAdapter<Group, GroupsAdapter.ViewHolder>(GroupDiffCallback) {

    override fun onBindViewHolder(holder: GroupsAdapter.ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.ViewHolder {
        return GroupsAdapter.ViewHolder(
            ItemWriteGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }
}
