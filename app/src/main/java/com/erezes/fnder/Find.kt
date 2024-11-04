package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.erezes.fnder.databinding.FragmentFindBinding
import com.erezes.fnder.retrofit.ApiService
import com.erezes.fnder.retrofit.RetrofitConnection
import com.erezes.fnder.retrofit.data.ItemsListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Find : Binding<FragmentFindBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentFindBinding {
        return FragmentFindBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = RetrofitConnection.getInstance(requireContext())
        val apiService = retrofit.create(ApiService::class.java)

        with(binding){
            profile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, Profile())
                    .remove(parentFragmentManager.findFragmentById(R.id.fragment_nav_bottom)!!)
                    .addToBackStack(null)
                    .commit()
            }

            view.setOnClickListener {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(search.windowToken, 0)
                search.clearFocus()
            }

            apiService.getItemsList().enqueue(object: Callback<ItemsListResponse> {
                override fun onResponse(
                    call: Call<ItemsListResponse>,
                    response: Response<ItemsListResponse>
                ) {
                    if(response.isSuccessful){
                        val postData = response.body()?.data
                        Log.d("Post", postData.toString())
                        PostList.layoutManager = LinearLayoutManager(context)
                        PostList.setHasFixedSize(true)
                        PostList.adapter = PostViewAdapter(postData)
                    }else{
                        Log.d("Post", "response unsuccessful")
                    }
                }

                override fun onFailure(call: Call<ItemsListResponse>, t: Throwable) {
                    Log.d("Post", "온패일러")
                }
            })
        }
    }
}