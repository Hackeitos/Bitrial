package com.bitrial.bitrial

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CardsHolderFragment : Fragment() {

    lateinit var requestQueue: RequestQueue
    lateinit var currentCardContainer: CardContainer
    lateinit var cardPlaceholderView: FrameLayout
    lateinit var textViewError: TextView
    lateinit var loadingLayout: ConstraintLayout
    lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity?.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            return null

        requestQueue = Volley.newRequestQueue(requireContext())

        return inflater.inflate(R.layout.fragment_cards_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.floatingActionButton)
        cardPlaceholderView = view.findViewById(R.id.card_placeholder)
        textViewError = view.findViewById(R.id.textViewError)
        loadingLayout = view.findViewById(R.id.loadingLayout)

        newCard()

        fab.setOnClickListener {
            currentCardContainer.flipCard()
        }

        fab.setOnLongClickListener {
            newCard()
            true
        }
    }

    private fun newCard() {
        setVisible(loadingLayout)

        Card.get(requestQueue, { card ->
            currentCardContainer = CardContainer(card)
            parentFragmentManager.beginTransaction()
                .replace(R.id.card_placeholder, currentCardContainer, "card").commit()
            setVisible(cardPlaceholderView)
        }, { error ->
            textViewError.text = "Error obteniendo tarjeta:\n\n${error.message}"
            setVisible(textViewError)
        })
    }

    private fun setVisible(view: View) {
        val list = listOf(loadingLayout, cardPlaceholderView, textViewError)

        list.forEach {
            if (view == it)
                it.visibility = View.VISIBLE
            else
                it.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }


}