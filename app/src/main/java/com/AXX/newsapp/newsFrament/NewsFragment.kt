package com.AXX.newsapp.newsFrament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.AXX.newsapp.API.ApiManager
import com.AXX.newsapp.API.model.Source
import com.AXX.newsapp.API.model.newsResponse.Article
import com.AXX.newsapp.API.model.newsResponse.NewsResponse
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class NewsFragment:Fragment() {

    lateinit var viewBinding : FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentNewsBinding.inflate(
        inflater,
        container,
        false)
         return viewBinding.root
    }

    var source:Source?=null
    fun changeSource(source: Source){
       this.source =source
        loadNews()
    }

    private fun loadNews() {

        changeLoadinVisability(true)

//        if (source!=null && source.id!=null)
//            ApiManager.getServices()
//                .getNews(sources = source?.id?:"")

        source?.id?.let {sourceId->
            ApiManager.getServices()
                .getNews(sources = sourceId)
                .enqueue(object :retrofit2.Callback<NewsResponse>{
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        changeLoadinVisability(false)
                        showError(t.message)
                    }

                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {

                        changeLoadinVisability(false)
                        if (response.isSuccessful){
                            showNewsList(response.body()?.articles)
                        return
                        }

                        val responseJson = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(responseJson,
                          NewsResponse::class.java,
                        )
                        showError(errorResponse.message)
                    }
                })
        }
    }

    val adapter = NewsAdapter(null)
    private fun showNewsList(articles: List<Article?>?) {
      adapter.changeData(articles)
    }

    fun changeLoadinVisability(isLoadingVisible:Boolean){
        viewBinding.progressBar.isVisible = isVisible
    }


    private fun showError(message: String?) {
        viewBinding.errorView.isVisible =true
        viewBinding.errorMessage.text = message
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        viewBinding.newsRecycler.adapter=adapter
    }
}