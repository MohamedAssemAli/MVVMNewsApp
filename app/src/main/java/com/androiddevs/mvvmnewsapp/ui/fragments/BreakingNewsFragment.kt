package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.ui.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.adapters.NewsLoadStateAdapter
import com.androiddevs.mvvmnewsapp.ui.adapters.PagedNewsAdapter
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val TAG = "BreakingNewsFragment"
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var pagedNewsAdapter: PagedNewsAdapter

    private var getNewsJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
//        setupRecyclerView()
        setRecyclerViewWithPagination()
        getPagedNews()
//        initAdapterWithFooterAndHeader()
//        getNews()
    }


    private fun getPagedNews() {
        // Make sure we cancel the previous job before creating a new one
        getNewsJob?.cancel()
        getNewsJob = lifecycleScope.launch {
            viewModel.getPagedBreakingNews("eg").collectLatest {
                pagedNewsAdapter.submitData(it)
            }
        }
    }

    private fun initAdapterWithFooterAndHeader() {
        pagedNewsAdapter = PagedNewsAdapter()
        rvBreakingNews.apply {
            adapter = pagedNewsAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { pagedNewsAdapter.retry() },
                footer = NewsLoadStateAdapter { pagedNewsAdapter.retry() }
            )

            pagedNewsAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                rvBreakingNews.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                paginationProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retry_button.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        pagedNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun getNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "onViewCreated: An error occurred : $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setRecyclerViewWithPagination() {

        pagedNewsAdapter = PagedNewsAdapter()
        rvBreakingNews.apply {
            adapter = pagedNewsAdapter
        }

        pagedNewsAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                paginationProgressBar.visibility = View.VISIBLE
            } else {
                paginationProgressBar.visibility = View.GONE

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
        }
    }
}