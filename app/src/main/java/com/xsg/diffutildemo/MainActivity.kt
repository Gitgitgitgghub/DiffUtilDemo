package com.xsg.diffutildemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsg.diffutildemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mBinding :ActivityMainBinding
    private lateinit var mViewModel :MainViewModel
    private lateinit var mAdapter: DiffUtilAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecyclerView()
        observeItemData()
        fetchData()
        mBinding.btnAdd.setOnClickListener(this)
        mBinding.btnDelete.setOnClickListener(this)
        mBinding.btnResetColor.setOnClickListener(this)
    }

    private fun initRecyclerView(){
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.adapter = DiffUtilAdapter(){
            mViewModel.onItemClick(it)
        }.apply {
            mAdapter = this
        }
    }

    private fun observeItemData(){
        mViewModel.mItemDataLiveData.observe(this){
            mAdapter.submit(it)
        }
    }

    private fun fetchData(){
        mViewModel.fetchData()
    }

    override fun onClick(v: View?) {
        v ?:return
        when(v){
            mBinding.btnAdd -> mViewModel.addOne()
            mBinding.btnDelete -> mViewModel.deleteOne()
            mBinding.btnResetColor -> mViewModel.resetColor()
        }
    }
}