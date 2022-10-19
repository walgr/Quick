package com.wpf.app.quick.demo.model

import android.os.Parcel
import android.os.Parcelable
import com.wpf.app.quick.demo.adapterholder.TestHolder
import com.wpf.app.quickrecyclerview.annotations.BindHolder
import com.wpf.app.quickrecyclerview.data.QuickItemData

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@com.wpf.app.quickrecyclerview.annotations.BindHolder(TestHolder::class)
class TestModel : com.wpf.app.quickrecyclerview.data.QuickItemData, Parcelable {
    var text: String = "1"

    constructor() {}
    constructor(text: String) {
        this.text = text
    }

    protected constructor(`in`: Parcel) {
        text = `in`.readString() ?: ""
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(text)
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