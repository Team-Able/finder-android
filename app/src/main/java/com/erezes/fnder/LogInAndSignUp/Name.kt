package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.erezes.fnder.databinding.FragmentNameBinding
import com.erezes.fnder.retrofit.ApiService
import com.erezes.fnder.retrofit.RetrofitConnection
import com.erezes.fnder.retrofit.data.SignUpRequest
import com.erezes.fnder.retrofit.data.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Name : Binding<FragmentNameBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentNameBinding {
        return FragmentNameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            view.setOnClickListener {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(nameInput.windowToken, 0)
                nameInput.clearFocus()
            }
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            nextButton.setOnClickListener {
                if (nameInput.text.toString().isNotEmpty()) {
                    val info = arguments?.getStringArray("idPassword")
                    val username = nameInput.text.toString()
                    val email = info?.get(0).toString()
                    val password = info?.get(1).toString()
                    val signUpRequest = SignUpRequest(username, email, password)
                    val apiService = RetrofitConnection.getInstance(requireContext()).create(ApiService::class.java)

                    apiService.signUp(signUpRequest).enqueue(object : Callback<SignUpResponse> {
                        override fun onResponse(
                            call: Call<SignUpResponse>, response: Response<SignUpResponse>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    requireContext(), "회원가입 성공: ${response.body()}", Toast.LENGTH_SHORT
                                ).show()
                                println("회원가입 성공: ${response.body()}")
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container_view_tag, LogIn())
                                    .addToBackStack(null).commit()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "실패했다: 1 : ${response.code()} || 2 : ${response.body()} || 3 : ${response.message()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                println("실패: ${response.code()}")
                                println("이거다 ${email} ${password} ${username}")
                            }
                        }

                        override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                            Toast.makeText(
                                requireContext(), "에러가 났어요 -> ${t.message}", Toast.LENGTH_SHORT
                            ).show()
                            println("에러가 났어요 -> ${t.message}")
                        }
                    })
                }
            }
        }
    }
}