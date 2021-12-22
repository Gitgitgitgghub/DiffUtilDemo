package com.xsg.diffutildemo

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Brant on 2021/12/17.
 */
class MainViewModel : ViewModel() {

    val mItemDataLiveData = MutableLiveData<List<ItemData>>()
    private val mData = mutableListOf<ItemData>()
    private var mRepeatCount = 0

    private enum class BgColor(val color: Int) {
        Default(Color.BLACK),
        Blue(Color.BLUE),
        Green(Color.GREEN),
        Red(Color.RED)
    }

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
        setRandomColor(itemData)
        mData[position] = itemData
        mItemDataLiveData.value = mData
    }

    private fun setRandomColor(data: ItemData){
        val color = BgColor.values().random().color
        if (color != data.bgColor){
            data.bgColor = color
        }else{
            setRandomColor(data)
        }
    }

    fun resetColor(){
        mData.forEachIndexed() { index ,item ->
            //這邊一樣需要copy
            item.copy().apply {
                bgColor = BgColor.Default.color
            }.also {
                mData[index] = it
            }
        }
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