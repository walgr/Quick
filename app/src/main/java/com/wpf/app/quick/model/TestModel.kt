package com.wpf.app.quick.model

import android.os.Parcel
import android.os.Parcelable
import com.wpf.app.quick.adapterholder.TestHolder
import com.wpf.app.quick.base.widgets.recyclerview.QuickItemData
import com.wpf.app.quick.base.widgets.recyclerview.HolderClass

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@HolderClass(TestHolder::class)
class TestModel(val text: String = "1") : QuickItemData(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestModel> {
        override fun createFromParcel(parcel: Parcel): TestModel {
            return TestModel(parcel)
        }

        override fun newArray(size: Int): Array<TestModel?> {
            return arrayOfNulls(size)
        }
    }


}