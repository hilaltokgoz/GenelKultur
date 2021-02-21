package com.genelkultur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_test.*


class TestFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        butonTest1.setOnClickListener {
            val action=TestFragmentDirections.actionTestFragmentToTestFragment1()
            Navigation.findNavController(it).navigate(action)
        }
        buttonTest2.setOnClickListener {
            val action=TestFragmentDirections.actionTestFragmentToTestFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        buttonTest3.setOnClickListener {
            val action=TestFragmentDirections.actionTestFragmentToTestFragment3()
            Navigation.findNavController(it).navigate(action)
        }
    }

}