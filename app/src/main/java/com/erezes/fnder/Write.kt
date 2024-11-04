package com.erezes.fnder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erezes.fnder.databinding.FragmentWriteBinding

class Write : Binding<FragmentWriteBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentWriteBinding {
        return FragmentWriteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}