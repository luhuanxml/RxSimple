package com.luhuan.rxsimple.constraint

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luhuan.rxsimple.R

class ConstraintAdapter : RecyclerView.Adapter<ConstraintAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder? {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_constraint,parent,
                false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder:Holder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
