package com.geekbrains.februarymaterial.ExtensionFun

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Функционал View отображения SnackBack
 */

fun View.showToastShort(
    text: String,
    length: Int = Toast.LENGTH_SHORT)
{
    Toast.makeText(context, text, length).show()
}

fun View.showSnackBarAction(
    text: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit,)
{
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBarNoAction(
    text: String,
    length: Int = Snackbar.LENGTH_SHORT)
{
    Snackbar.make(this, text, length).show()
}
/**
 * Функционал View отображения SnackBack
 */

/**
 * Функционал View отображения клавиатуры (например при вводе текста) или нажатии на кнопку
 */

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try { val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}
/**
 * Функционал View отображения клавиатуры (например при вводе текста) или нажатии на кнопку
 */