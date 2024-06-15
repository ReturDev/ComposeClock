package com.retur.composeclock.ui.core.model

import android.content.Context
import com.retur.composeclock.R
import java.util.Locale

fun getAppLocale(context : Context) : Locale = Locale(context.getString(R.string.app_language))