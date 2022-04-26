package com.example.moneytrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val content = inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
        buttonDetailHandler(content)

        return content
    }

    private fun buttonDetailHandler(content: View) {
        val btnDetailOutcome: CardView = content.findViewById(R.id.btnDetailOutcome)
        btnDetailOutcome.setOnClickListener {
            val intent = Intent(content.context, DetailScreenActivity::class.java)
            startActivity(intent)
        }
    }
}