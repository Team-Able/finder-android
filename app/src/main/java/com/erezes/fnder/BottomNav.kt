package com.erezes.fnder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erezes.fnder.databinding.FragmentBottomNavBinding

class BottomNav : Binding<FragmentBottomNavBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentBottomNavBinding {
        return FragmentBottomNavBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val buttons = listOf(find, main, write)
            val texts = listOf(findText, mainText, writeText)
            val move = { page: Fragment ->
                if (parentFragmentManager.findFragmentById(R.id.fragment_container_view_tag)?.javaClass?.simpleName != page.javaClass.simpleName) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view_tag, page)
                        .commit()
                }
            }
            mainText.isSelected = true
            main.isSelected = true
            buttons.forEach { button ->
                button.setOnClickListener {
                    move(
                        when (button) {
                            find -> Find()
                            main -> Main()
                            write -> Write()
                            else -> return@setOnClickListener
                        }
                    )
                    buttons.forEach { it.setBackgroundResource(R.drawable.nav_icon_background) }
                    buttons.forEach { it.isSelected = false }
                    texts.forEach { it.isSelected = false }
                    button.setBackgroundResource(R.drawable.nav_icon_background_colored)
                    button.isSelected = true
                    texts[buttons.indexOf(button)].isSelected = true
                }
            }
        }
    }
}