package com.example.apphourplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphourplay.databinding.FragmentHorasDeberesBinding

class HorasDeberesFragment : Fragment() {

    private lateinit var binding: FragmentHorasDeberesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorasDeberesBinding.inflate(inflater)
        return binding.root
    }

}