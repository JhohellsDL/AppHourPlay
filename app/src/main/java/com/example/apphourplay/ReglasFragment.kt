package com.example.apphourplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphourplay.databinding.FragmentReglasBinding

class ReglasFragment : Fragment() {

    private lateinit var binding: FragmentReglasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReglasBinding.inflate(inflater)
        return binding.root
    }
}