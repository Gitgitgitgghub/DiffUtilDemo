package com.xsg.diffutildemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Brant on 2021/12/17.
 */
class MainViewModel : ViewModel() {

    val mItemDataLiveData = MutableLiveData<List<ItemData>>()
    private val mData = mutableListOf<ItemData>()
    private var mRepeatCount = 0;

    fun fetchData() {
        repeat(10){
            mData.add(ItemData(mRepeatCount))
            mRepeatCount++
        }
        mItemDataLiveData.value = mData
    }

    fun addOne(){
        mData.add(ItemData(mRepeatCount))
        mRepeatCount++
        mItemDataLiveData.value = mData
    }

    fun deleteOne(){
        mData.removeAt(0)
        mItemDataLiveData.value = mData
    }

    fun onItemClick(position :Int){
        val itemData = mData[position].copy().apply {
            clickCount++
        }
        mData[position] = itemData
        mItemDataLiveData.value = mData
    }

//    fun onItemClick(position :Int){
//        mData[position].apply {
//            clickCount++
//            isDataChange = true
//        }
//        mItemDataLiveData.value = mData
//    }
}