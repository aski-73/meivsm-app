package com.aveyon.meivsm.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aveyon.meivsm.R
import com.aveyon.meivsm.model.entities.ExternallyOwnedAccount
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.web3j.utils.Convert
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("crowdfundingDesc")
fun crowdfundingDesc(view: View, state: String?) {
    if (state == null)
        return

    var desc = view.resources.getString(R.string.crowdfunding_failed_desc)

    if (state == "SUCCESSFUL")
        desc = view.resources.getString(R.string.crowdfunding_successful_desc)

    view.findViewById<TextView>(R.id.crowdfunding_end_desc).text = desc
}

@BindingAdapter("crowdfundingHeader")
fun crowdfundingHeader(view: View, state: String?) {
    if (state == null)
        return

    var header = view.resources.getString(R.string.crowdfunding_failed)

    if (state == "SUCCESSFUL")
        header = view.resources.getString(R.string.crowdfunding_successful)

    view.findViewById<TextView>(R.id.crowdfunding_end_header).text = header
}

/**
 * Sets endDate text. If endDate is exceeded the text will be different if endDate is still in
 * the future
 */
@BindingAdapter("endDate")
fun endDate(view: TextView, endDate: BigInteger?) {
    if (endDate != null) {
        // Mit Faktor 1000 multiplizieren, um Millisekunden zu erhalten
        // Timestamp im Contract wird nämlich als Sekundenwert gespeichert. Date arbeitet jedoch
        // mit ms
        var date = Date(endDate.toLong() * 1000)
        var dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        // texts depend on wheters endDate is over due or not
        if (Date().time > date.time) {
            view.text =
                view.resources.getString(
                    R.string.crowdfunding_ended_on_date,
                    dateFormat.format(date.time)
                )
        } else {
            view.text = view.resources.getString(
                R.string.crowdfunding_active_till_end_date,
                dateFormat.format(date.time)
            )
        }
    }
}

/**
 * Crowdfunding Goal and current sum. Since both values are in wei, they will be transformed
 * to Ether for better presentation
 */
@BindingAdapter("goal", "sum")
fun sumToGoalRatio(view: TextView, goal: BigInteger?, sum: BigInteger?) {
    if (goal != null && sum != null) {
        var convertedSum = Convert.fromWei(sum.toString(), Convert.Unit.ETHER).toString()
        var convertedGoal = Convert.fromWei(goal.toString(), Convert.Unit.ETHER).toString()
        view.text =
            view.resources.getString(R.string.crowdfunding_sum_to_goal, convertedSum, convertedGoal)
    } else {
        view.text = "..."
    }
}

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

@BindingAdapter("isValidNumber")
fun isValidNumber(view: TextInputLayout, value: String) {
    var textInput = view.findViewById<TextInputEditText>(R.id.crowdfunding_goal)
    if (!Validator.isValidNumber(value) && textInput.isDirty)
        view.error = view.resources.getString(R.string.error_invalid_number, 1, 6)
    else
        view.error = ""

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

        /**
         * Contract support values up to 2^256 (unsigned). At the moment
         * less is needed
         */
        fun isValidNumber(value: String): Boolean {
            return Regex("[0-9]{1,6}").matches(value)
        }
    }
}