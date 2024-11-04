package com.erezes.fnder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.erezes.fnder.databinding.FragmentClauseCheckBinding

class ClauseCheck : Binding<FragmentClauseCheckBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentClauseCheckBinding {
        return FragmentClauseCheckBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            nextButton.setOnClickListener {
                if (personalCheck.isChecked and useCheck.isChecked) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag, Email()).addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "약관 동의 해", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}