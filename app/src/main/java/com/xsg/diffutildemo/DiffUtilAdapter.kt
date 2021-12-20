package com.xsg.diffutildemo

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xsg.diffutildemo.databinding.ItemItemDataBinding

/**
 * Created by Brant on 2021/12/17.
 */
class DiffUtilAdapter(private val mItemClickListener :(position :Int) -> Unit) : RecyclerView.Adapter<DiffUtilAdapter.Companion.ItemDataViewHolder>() {


    companion object{
        class ItemDataViewHolder(private val mBinding: ItemItemDataBinding) : RecyclerView.ViewHolder(mBinding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(data :ItemData){
                mBinding.tvItemID.text = "ID :${data.id} / 點了${data.clickCount}次"
            }
        }
    }

    private val mDiffCallback = object : DiffUtil.ItemCallback<ItemData>() {
        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            //return !oldItem.isDataChange
            return oldItem.clickCount == newItem.clickCount
        }

    }

    private val mAsyncDiffer = AsyncListDiffer<ItemData>(this,mDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDataViewHolder {
        return ItemDataViewHolder(ItemItemDataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemDataViewHolder, position: Int) {
        holder.bind(mAsyncDiffer.currentList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return mAsyncDiffer.currentList.size
    }

    fun submit(data :List<ItemData>){
        //mAsyncDiffer.submitList(data)
        mAsyncDiffer.submitList(mutableListOf<ItemData>().apply {
            addAll(data)
        })
    }
}