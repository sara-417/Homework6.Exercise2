package com.slayton.msu.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.slayton.msu.criminalintent.databinding.FragmentCrimeDetailBinding
import java.util.*

class CrimeDetailsFragment :Fragment() {

    internal lateinit var crime:Crime
   private var _binding: FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstancesState: Bundle?) {
        super.onCreate(savedInstancesState)

        crime = Crime(
            UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false,
            requiresPolice = false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    //Wiring up views in a fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
                    //lsitener for edit text
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crime = crime.copy(title = text.toString())

            }

            //listener for button
            crimeDate.apply{
                text = crime.date.toString()
                isEnabled = false
            }

            //listener for checkbox
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crime = crime.copy(isSolved = isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}