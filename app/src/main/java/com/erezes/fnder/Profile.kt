package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erezes.fnder.databinding.FragmentProfileBinding
import com.erezes.fnder.retrofit.ApiService
import com.erezes.fnder.retrofit.RetrofitConnection
import com.erezes.fnder.retrofit.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : Binding<FragmentProfileBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = RetrofitConnection.getInstance(requireContext())
        val apiService = retrofit.create(ApiService::class.java)

        with(binding){
            apiService.getUser().enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        val userData = response.body()
                        userName.text = userData?.data?.username.toString()
                    }else{
                        Log.d("profile","실패")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("profile", "온패일러")
                }
            })



            logout.setOnClickListener{
                val sharedPreferences = requireContext().getSharedPreferences("tokens", Context.MODE_PRIVATE)
                sharedPreferences.edit()
                    .remove("accessToken")
                    .remove("refreshToken")
                    .apply()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, LogIn())
                    .commit()
            }
            backButton.setOnClickListener {
                parentFragmentManager
                    .popBackStack()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_nav_bottom, BottomNav())
                    .commit()
            }
        }
    }
}