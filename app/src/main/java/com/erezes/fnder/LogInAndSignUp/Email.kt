package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.erezes.fnder.databinding.FragmentEmailBinding


class Email : Binding<FragmentEmailBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentEmailBinding {
        return FragmentEmailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            view.setOnClickListener {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(emailInput.windowToken, 0)
                emailInput.clearFocus()
            }
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            emailInputLayout.setEndIconOnClickListener {
                emailInput.text?.clear()
            }
            nextButton.setOnClickListener {
                val email = emailInput.text.toString()
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    val password = Password()
                    password.arguments = Bundle().apply {
                        putString("email", email)
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag, password).addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "형식이 올바르지 않아요!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}