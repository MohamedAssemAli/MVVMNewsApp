package com.androiddevs.mvvmnewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import kotlinx.android.synthetic.main.item_load_state_footer.view.*

/**
 * Created by Mohamed Assem on 22-Aug-2020.
 * mohamed.assem.ali@gmail.com
 * https://github.com/MohamedAssemAli
 */
class NewsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NewsLoadStateAdapter.NewsLoadStateViewHolder>() {

    class NewsLoadStateViewHolder(
        itemView: View, retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.itemView.apply {
            if (loadState is LoadState.Error) {
                error_msg.text = loadState.error.localizedMessage
            }
            progress_bar.isVisible = loadState is LoadState.Loading
            retry_button.isVisible = loadState !is LoadState.Loading
            error_msg.isVisible = loadState !is LoadState.Loading
            retry_button.setOnClickListener { retry.invoke() }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NewsLoadStateViewHolder {
        return NewsLoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_load_state_footer,
                    parent,
                    false
                ),
            retry
        )
    }
}