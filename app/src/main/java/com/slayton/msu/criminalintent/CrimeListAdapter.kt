package com.slayton.msu.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.slayton.msu.criminalintent.databinding.ListItemCrimeBinding

// I changed CrimeHolder to NormalCrimeHolder to be better differentiated from SeriousCrimeHolder
class NormalCrimeHolder (
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

// in creating the SeriousCrimeHolder, i copied and pasted the NormalCrimeHolder and made small changes
class SeriousCrimeHolder (
   private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
//        change the button's visibility to true
        binding.reportButton.isVisible = true

//        I didn't know if you wanted the crime itself to be clickable like it is with normal crimes, so i left it in.
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()

//            added an event listener to the reportButton to make a toast notifiying the user that the police were contacted
            binding.reportButton.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} reported to the police.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

    class CrimeListAdapter(
        private val crimes: List<Crime>
        // replace CrimeHolder with the RecyclerView.ViewHolder
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
//    replace CrimeHolder with RecyclerView.ViewHolder
        ): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return when (viewType) {
//  implement logic to return the ViewHolder based on the type of crime
                1 -> return SeriousCrimeHolder(binding)
                else -> return NormalCrimeHolder(binding)
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val crime = crimes[position]
//      bind the correct ViewHolder based on what is returned by onCreateViewHolder
                // chatGPT helped me write the following block
            when (holder) {
                is NormalCrimeHolder -> {
                    (holder).bind(crime)
                }
                is SeriousCrimeHolder -> {
                    holder.bind(crime)
                }
            }
        }

        override fun getItemCount() = crimes.size

        //    To understand how getItemViewType works, I consulted the documentation provided
//    by android studio as well as asked chatGPT to explain the funciton to me with
//    greater detail.
        override fun getItemViewType(position: Int): Int {
//  if the crime requires police, the function will return a 1, otherwise it will
//    return its default value
            var viewTypeId = 0
            val crime = crimes[position]
            if (crime.requiresPolice) {
                viewTypeId = 1
            }

            return viewTypeId
        }
    }
