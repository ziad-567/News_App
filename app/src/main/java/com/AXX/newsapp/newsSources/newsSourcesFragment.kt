package com.AXX.newsapp.newsSources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.AXX.newsapp.API.ApiManager
import com.AXX.newsapp.API.model.Source
import com.AXX.newsapp.API.model.SourcesResponse
import com.AXX.newsapp.newsFrament.NewsFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewaSourcesBinding
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class newsSourcesFragment:Fragment(){

    lateinit var viewBinding:FragmentNewaSourcesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        viewBinding = FragmentNewaSourcesBinding.inflate(inflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getNewsSources()


    }

    val newsfragment = NewsFragment()
    private fun initViews() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,newsfragment)
            .commit()
        viewBinding.tryAgain.setOnClickListener{
            viewBinding.errorView.isVisible =false
            getNewsSources()
        }
    }

    private fun showNewSources(sources: List<Source?>?) {
       viewBinding.errorView.isVisible = false
        viewBinding.progressBar.isVisible =false

        sources?.forEach{source->
            val tab = viewBinding.tabLayout.newTab()
            tab.text =source?.name
            tab.tag =source
            viewBinding.tabLayout.addTab(tab)
        }
        viewBinding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
              val source = tab?.tag as Source
                newsfragment.changeSource(source)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                newsfragment.changeSource(source)
            }
        })
        viewBinding.tabLayout.getTabAt(0)?.select()
    }

    fun changeLoadinVisability(isLoadingVisible:Boolean){
        viewBinding.progressBar.isVisible = isVisible
     }
    private fun getNewsSources() {
        changeLoadinVisability(true)
        ApiManager
            .getServices()
            .getNewsSources()
            .enqueue(object :Callback<SourcesResponse>{
                override fun onFailure(
                    call: Call<SourcesResponse>,
                    t: Throwable,
                ) {
                  changeLoadinVisability(false)
                  showError(t.message)
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    changeLoadinVisability(false)
                    if (response.isSuccessful){
                        showNewSources(response.body()?.sources)
                        return
                    }
                    val responseJson =response.errorBody()?.string()
                    val errorResponse =Gson().fromJson(responseJson,
                        SourcesResponse::class.java)
                    showError(errorResponse.usermessage)//look min 20 at part 2
                }
            })
    }

    private fun showError(message: String?) {
        viewBinding.errorView.isVisible =true
        viewBinding.errorMessage.text = message
    }

}