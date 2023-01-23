package com.example.apphourplay

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphourplay.databinding.FragmentSorteoBinding

class SorteoFragment : Fragment() {

    private lateinit var binding: FragmentSorteoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSorteoBinding.inflate(layoutInflater)
        return binding.root
    }
}