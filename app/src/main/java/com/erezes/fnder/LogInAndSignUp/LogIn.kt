package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.erezes.fnder.databinding.FragmentLogInBinding
import com.erezes.fnder.retrofit.ApiService
import com.erezes.fnder.retrofit.RetrofitConnection
import com.erezes.fnder.retrofit.TokenManager
import com.erezes.fnder.retrofit.data.LogInRequest
import com.erezes.fnder.retrofit.data.LogInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogIn : Binding<FragmentLogInBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentLogInBinding {
        return FragmentLogInBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = RetrofitConnection.getInstance(requireContext())
        val apiService = retrofit.create(ApiService::class.java)

        with(binding) {
            view.setOnClickListener {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(emailInput.windowToken, 0)
                imm.hideSoftInputFromWindow(passwordInput.windowToken, 0)
                emailInput.clearFocus()
                passwordInput.clearFocus()
            }

            signUp.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, ClauseCheck()).addToBackStack(null)
                    .commit()
            }

            logInButton.isEnabled = false
            emailInputLayout.setEndIconOnClickListener {
                emailInput.text?.clear()
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    logInButton.isEnabled =
                        (passwordInput.text.toString().length in 8..31) && (emailInput.text.toString()
                            .isNotEmpty())
                }

                override fun afterTextChanged(s: Editable?) {}
            }
            emailInput.addTextChangedListener(textWatcher)
            passwordInput.addTextChangedListener(textWatcher)

            logInButton.setOnClickListener {
                val logInData =
                    LogInRequest(emailInput.text.toString(), passwordInput.text.toString())
                apiService.logIn(logInData).enqueue(object : Callback<LogInResponse> {
                    override fun onResponse(
                        call: Call<LogInResponse>, response: Response<LogInResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                            Log.d("LogIn", "로그인 성공 ${response.code()}")
                            TokenManager(context!!.getSharedPreferences("tokens", Context.MODE_PRIVATE)).saveTokens(
                                response.body()!!.data.accessToken,
                                response.body()!!.data.refreshToken
                            )
                            parentFragmentManager.popBackStack(null, 0)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view_tag, Main())
                                .replace(R.id.fragment_nav_bottom, BottomNav())
                                .replace(R.id.fragment_profile_bar, ProfileBar())
                                .commit()
                        } else {
                            Toast.makeText(
                                requireContext(), "로그인 실패 ${response.code()}", Toast.LENGTH_LONG
                            ).show()
                            Log.d("LogIn", "로그인 실패 ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                        Log.d("LogIn", "에런데~? ${t.message}")
                    }
                })
            }
        }
    }
}