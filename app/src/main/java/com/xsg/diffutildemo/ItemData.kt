package com.xsg.diffutildemo

import android.graphics.Color

/**
 * Created by Brant on 2021/12/17.
 */
data class ItemData(var id :Int,var clickCount :Int = 0,var isDataChange :Boolean = false,var bgColor :Int = Color.BLACK)
