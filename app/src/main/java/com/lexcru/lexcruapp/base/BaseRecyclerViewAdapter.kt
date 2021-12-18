package com.lexcru.lexcruapp.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.utils.LoadingViewHolder
import com.lexcru.lexcruapp.utils.VIEW_ITEM
import com.lexcru.lexcruapp.utils.VIEW_LOADING

abstract class BaseRecyclerViewAdapter(private var list:MutableList<*>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @LayoutRes
    abstract fun getItemLayout():Int

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_LOADING -> {
                val emptyView = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_item,parent,false)
                LoadingViewHolder(emptyView)
            }
            else -> MyViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayout(),parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position:Int) = if(list[position] == null && list.isNotEmpty()) VIEW_LOADING else VIEW_ITEM

    fun getMyItemViewType(position: Int) = if(list[position] == null && list.isNotEmpty()) VIEW_LOADING else VIEW_ITEM

    open fun onMyViewHolderInit(viewHolder:MyViewHolder,itemView: View) = Unit

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            onMyViewHolderInit(this,itemView)
        }
    }
}