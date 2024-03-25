package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class BaseResponseS<Data> @JvmOverloads constructor(
    var data: Data? = null,
    private var code: String? = null,
    private var errorMessage: String? = null
): BaseResponseIS<Data> {

    override var codeI: String? = null
        get() = code
        set(value) {
            field = value
            code = value
        }
    override var errorI: String? = null
        get() = errorMessage
        set(value) {
            field = value
            errorMessage = value
        }
    override var dataI: Data? = null
        get() = data
        set(value) {
            field = value
            data = value
        }
}