package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erezes.fnder.databinding.FragmentMainBinding
import com.erezes.fnder.retrofit.data.SlideItem

class Main : Binding<FragmentMainBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            profile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, Profile())
                    .remove(parentFragmentManager.findFragmentById(R.id.fragment_nav_bottom)!!)
                    .addToBackStack(null)
                    .commit()
            }
            val slideItems = listOf(
                SlideItem(R.drawable.banner),
                SlideItem(R.drawable.banner),
                SlideItem(R.drawable.banner),
                SlideItem(R.drawable.banner),
                SlideItem(R.drawable.banner)
            )

            viewPager.adapter = SlideAdapter(slideItems)
            viewPager.setCurrentItem(2, false)
            viewPager.offscreenPageLimit = 1

            accessTokenRemove.setOnClickListener{
                val preferences = context?.getSharedPreferences("tokens", Context.MODE_PRIVATE)
                Log.d("main", "이건 accessToken ${preferences?.getString("accessToken", null)}")
                preferences!!.edit().remove("accessToken").apply()
                Log.d("main", "이건 accessToken ${preferences.getString("accessToken", null)}")
            }
        }
    }
}