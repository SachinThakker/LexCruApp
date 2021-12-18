/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 https://www.spaceotechnologies.com
 *
 * Permissions is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.lexcru.lexcruapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson


object PreferenceHelper {

    // android.preference.PreferenceManager is deprecated so need to use
    // androidx.preference.PreferenceManager for that we have to add
    // implementation "androidx.preference:preference-ktx:1.1.0" dependency
    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun Context.customPrefs(): SharedPreferences =
        getSharedPreferences("AppPref", Context.MODE_PRIVATE)

    private lateinit var myPref: SharedPreferences


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun init(context: Context) {
        myPref = context.customPrefs()
    }


    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }



    /**
     * Save user object to preference
     */
    fun saveUserObject(user: com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult?) {
        myPref[PREF_LOGIN_DATA] = Gson().toJson(user)
    }

    fun resetUserObject() {
        myPref[PREF_LOGIN_DATA] = ""
    }

    /**Return User object, Which is saved on login, signup and edit profile */
    fun getUserDetailsObject(): com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult? {
        var userData: com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult? = null
        try {
            userData = Gson().fromJson(myPref[PREF_LOGIN_DATA, ""], com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult::class.java)
        }catch (e : Exception){
            e.printStackTrace()
        }
        return userData
    }

    fun saveAppUIMode(appUIMode:Int){
        myPref[PREF_APP_UI_MODE] = appUIMode
    }

    fun getAppUIMode():Int{
        return myPref[PREF_APP_UI_MODE, APP_UI_MODE_DAY].nullSafe(APP_UI_MODE_DAY)
    }

    fun saveFCMToken(fcmToken:String){
        myPref[PREF_FCM_TOKEN] = fcmToken
    }

    fun getFCMToken():String?{
        return myPref[PREF_FCM_TOKEN,""].nullSafe()
    }


}