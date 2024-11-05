package com.erezes.fnder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erezes.fnder.databinding.FragmentProfileBarBinding

class ProfileBar : Binding<FragmentProfileBarBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBarBinding {
        return FragmentProfileBarBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_tag, Profile())
                .remove(parentFragmentManager.findFragmentById(R.id.fragment_nav_bottom)!!)
                .addToBackStack(null)
                .remove(parentFragmentManager.findFragmentById(R.id.fragment_profile_bar)!!)
                .addToBackStack(null)
                .commit()
        }


    }
}