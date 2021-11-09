package com.hs_worms.android.roomrepositoryexample.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hs_worms.android.roomrepositoryexample.model.ElementListViewModel
import com.hs_worms.android.roomrepositoryexample.R
import com.hs_worms.android.roomrepositoryexample.model.Element


class ElementListFragment : Fragment() {

    interface ActivityCallbacks {
        fun onNavigationSelected(elementNr: Int)
    }

    private var activityCallbacks: ActivityCallbacks? = null

    private val viewmodel: ElementListViewModel by activityViewModels()

    private lateinit var elementListrecyclerView: RecyclerView
    private var adapter: ElementAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_1, container, false)

        elementListrecyclerView = view.findViewById(R.id.element_list_recycler_view)
        adapter = context?.let { ElementAdapter(it) }
        elementListrecyclerView.adapter = adapter
        elementListrecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.elements.observe(
            viewLifecycleOwner,
            Observer { elements ->
                elements?.let {
                    adapter?.setElements(elements)
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityCallbacks = context as ActivityCallbacks?
    }

    override fun onDetach() {
        super.onDetach()
        activityCallbacks = null
    }

    private inner class ElementHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var element: Element

        val titleTextView: TextView = itemView.findViewById(R.id.element_list_item_title)
        val elementButton: Button = itemView.findViewById(R.id.element_list_item_button)

        init {
            elementButton.setOnClickListener(this)
        }

        fun bind(element: Element) {
            this.element = element
            titleTextView.text = element.title
        }

        override fun onClick(v: View) {
            activityCallbacks?.onNavigationSelected(element.number)
            elementListrecyclerView.adapter?.notifyItemMoved(1, element.number)
        }

    }

    private inner class ElementAdapter internal constructor(context: Context) : RecyclerView.Adapter<ElementHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var elements = emptyList<Element>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
            val view = inflater.inflate(R.layout.element_list_item, parent, false)
            return ElementHolder(view)
        }

        override fun getItemCount() = elements.size

        override fun onBindViewHolder(holder: ElementHolder, position: Int) {
            val element = elements[position]
            holder.bind(element)
        }

        internal fun setElements(elements: List<Element>) {
            this.elements = elements
            notifyDataSetChanged()
        }

    }

}


