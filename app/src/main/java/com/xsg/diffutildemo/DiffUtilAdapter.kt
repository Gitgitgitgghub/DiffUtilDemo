package com.xsg.diffutildemo

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
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
                setClickCount(data)
                setBgColor(data)
            }

            fun setClickCount(data :ItemData){
                mBinding.tvItemID.text = "ID :${data.id} / 點了${data.clickCount}次"
            }

            fun setBgColor(data :ItemData){
                mBinding.cardParent.setCardBackgroundColor(data.bgColor)
            }
        }
    }

    enum class Payload{
        ClickCount,
        BgColor
    }

    private val mDiffCallback = object : DiffUtil.ItemCallback<ItemData>() {
        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            //return !oldItem.isDataChange
            return oldItem.clickCount == newItem.clickCount &&
                    oldItem.bgColor == newItem.bgColor
        }

        override fun getChangePayload(oldItem: ItemData, newItem: ItemData): Any? {
            val payloadSet = mutableSetOf<Payload>()
            if (oldItem.clickCount != newItem.clickCount){
                payloadSet.add(Payload.ClickCount)
            }
            if (oldItem.bgColor != newItem.bgColor){
                payloadSet.add(Payload.BgColor)
            }
            //如果沒有任何資料變化回傳null即可
            return if (payloadSet.isEmpty()) null else payloadSet
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

    override fun onBindViewHolder(holder: ItemDataViewHolder, position: Int, payloads: MutableList<Any>) {
        //如果payload為空，則呼叫兩參數的進行全面刷新
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position)
            return
        }
        val payloadSet = payloads.first()
        if (payloadSet !is MutableSet<*>) return
        //依照payload不同依次調用對應的刷新UI方法
        payloadSet.forEach {
            if (it != null && it is Payload){
                Log.d("kkk", "onBindViewHolder position: $position payload: $it")
                when(it){
                    Payload.ClickCount -> holder.setClickCount(mAsyncDiffer.currentList[position])
                    Payload.BgColor -> holder.setBgColor(mAsyncDiffer.currentList[position])
                }
            }
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