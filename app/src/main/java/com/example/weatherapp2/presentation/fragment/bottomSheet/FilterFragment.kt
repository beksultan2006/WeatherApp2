package com.example.weatherapp2.presentation.fragment.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp2.databinding.FragmentFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment(private val onClick: Result): BottomSheetDialogFragment() {

    private val binding: FragmentFilterBinding by lazy {
        FragmentFilterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {

            val name = binding.edName.text.toString()

            onClick.click(name)
            onDestroyView()
        }
    }


    interface Result {
        fun click(name: String)
    }

}