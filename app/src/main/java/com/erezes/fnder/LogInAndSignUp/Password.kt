package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.erezes.fnder.databinding.FragmentPasswordBinding

class Password : Binding<FragmentPasswordBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPasswordBinding {
        return FragmentPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            view.setOnClickListener {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(passwordCheckInput.windowToken, 0)
                imm.hideSoftInputFromWindow(passwordInput.windowToken, 0)
                passwordCheckInput.clearFocus()
                passwordInput.clearFocus()
            }
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            nextButton.setOnClickListener {
                if (passwordInput.text.toString() == passwordCheckInput.text.toString() && passwordInput.text.toString()
                        .isNotEmpty()
                ) {
                    val info = arrayOf(arguments?.getString("email"), passwordInput.text.toString())
                    val name = Name()
                    name.arguments = Bundle().apply {
                        putStringArray("idPassword", info)
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag, name).addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}