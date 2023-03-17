package com.cheocharm.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.cheocharm.presentation.databinding.ItemGroupBinding
import com.cheocharm.presentation.model.GroupModel
import com.cheocharm.presentation.ui.write.GroupDiffCallback
import com.cheocharm.presentation.ui.write.GroupsAdapter

class GroupsPagingAdapter(private val onClick: (GroupModel) -> Unit) :
    PagingDataAdapter<GroupModel, GroupsAdapter.ViewHolder>(GroupDiffCallback) {

    override fun onBindViewHolder(holder: GroupsAdapter.ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.ViewHolder {
        return GroupsAdapter.ViewHolder(
            ItemGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }
}
