package com.aveyon.meivsm.ui

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.fragment.app.findFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.aveyon.meivsm.R
import com.aveyon.meivsm.db.ExternallyOwnedAccount
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("showHelloText")
fun showHelloText(view: TextView, eoas: List<ExternallyOwnedAccount>?) {
    if (!eoas.isNullOrEmpty())
        view.text = view.resources.getString(R.string.accounts_welcome_msg, eoas[0].name)
}

/**
 * String with any word character: [a-zA-Z_0-9], any white space: [ \t\n\x0B\f\r]
 * and hyphen
 * Max Length 35 Characters
 */
@BindingAdapter("isRegularName")
fun isRegularName(view: TextInputLayout, value: String) {
    var regex = Regex("[\\s\\w\\-]{35}")
    if (!regex.matches(value) && view.isDirty) {
        view.error = "test"
    }
}

/**
 * Validate if 20 Byte Hex String
 */
@BindingAdapter("isPublicAddress")
fun isPublicAddress(view: View, value: String) {
    var regex = Regex("0x[a-fA-F0-9]{40}")
}

/**
 * Validate if 32 Byte Hex String
 */
@BindingAdapter("isPrivateKey")
fun isPrivateKey(view: View, value: String) {
    var regex = Regex("0x[a-fA-F0-9]{64}")
}

class Validator {
    companion object {
        fun isValidRegularName(value: String): Boolean {
            return Regex("[\\s\\w\\-]{35}").matches(value)
        }
        fun isValidPublicAddress(value: String): Boolean {
            return Regex("0x[a-fA-F0-9]{40}").matches(value)
        }
        fun isValidPrivateKey(value: String): Boolean {
            return Regex("0x[a-fA-F0-9]{64}").matches(value)
        }
    }
}