package com.example.testsample

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

class EmailValidator : TextWatcher {

    companion object {
        private val EMAIL_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )

        fun isValid(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }
    }

    private var mIsValid = false

    override fun afterTextChanged(s: Editable?) {
        mIsValid = Companion.isValid(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isValid() = mIsValid

}
