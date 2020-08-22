//package com.androiddevs.mvvmnewsapp.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.RecyclerView
//import com.androiddevs.mvvmnewsapp.R
//import kotlinx.android.synthetic.main.news_load_state_footer_view_item.*
//
//class NewsLoadStateViewHolder(
//    retry: () -> Unit
//) : RecyclerView.ViewHolder() {
//
//    init {
//        retryButton.setOnClickListener { retry.invoke() }
//    }
//
//    fun bind(loadState: LoadState) {
//        if (loadState is LoadState.Error) {
//            binding.errorMsg.text = loadState.error.localizedMessage
//        }
//        binding.progressBar.isVisible = loadState is LoadState.Loading
//        binding.retryButton.isVisible = loadState !is LoadState.Loading
//        error_msg.isVisible = loadState !is LoadState.Loading
//    }
//
//    companion object {
//        fun create(parent: ViewGroup, retry: () -> Unit): NewsLoadStateViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.news_load_state_footer_view_item, parent, false)
//            val binding = ReposLoadStateFooterViewItemBinding.bind(view)
//            return NewsLoadStateViewHolder(binding, retry)
//        }
//    }
//}
