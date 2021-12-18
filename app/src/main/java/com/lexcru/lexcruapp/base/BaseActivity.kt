package com.lexcru.lexcruapp.base

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.utils.APP_UI_MODE_AUTO
import com.lexcru.lexcruapp.utils.APP_UI_MODE_DAY
import com.lexcru.lexcruapp.utils.APP_UI_MODE_NIGHT
import com.lexcru.lexcruapp.utils.PreferenceHelper
import com.lexcru.lexcruapp.utils.PreferenceHelper.customPrefs
import com.kbeanie.multipicker.api.CameraImagePicker
import com.kbeanie.multipicker.api.ImagePicker
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback
import com.kbeanie.multipicker.api.entity.ChosenImage
import com.theartofdev.edmodo.cropper.CropImage
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseActivity<V : BaseViewModel>: AppCompatActivity(), ImagePickerCallback {

    abstract val modelClass:KClass<V>
    abstract fun getLayout():Int
    lateinit var viewModel: V

    lateinit var prefs: SharedPreferences
    private var imagePicker: ImagePicker? = null
    private var cameraPicker: CameraImagePicker? = null
    private var pickerPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = customPrefs()
        PreferenceHelper.init(this)
        setContentView(getLayout())
        createViewModel(savedInstanceState)
        getDataFromIntent()
        init()
        setListeners()
        addObserver()
    }

    open fun addObserver(){}
    open fun removeObserver(){}
    protected abstract fun init()
    protected open fun getDataFromIntent(){}
    protected abstract fun setListeners()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.writeTo(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObserver()
    }

    private fun createViewModel(savedInstanceState: Bundle?) {
        this.viewModel = getViewModel(clazz = modelClass)
        this.viewModel.readFrom(savedInstanceState)
    }

    fun hideStatusBar(){
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/
    }

    fun replaceFragment(
        fragmentContainerID: Int,
        fragmentManager: FragmentManager,
        fragmentToReplace: Fragment?,
        commitAllowingStateLoss: Boolean,
        argument: Bundle? = null
    ):Boolean{
        if(fragmentToReplace == null || fragmentManager == null){
            return false
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        argument?.let {
            fragmentToReplace.arguments = argument
        }
        fragmentTransaction.replace(
            fragmentContainerID,
            fragmentToReplace,
            fragmentToReplace.javaClass.simpleName
        )
        if(!commitAllowingStateLoss){
            fragmentTransaction.commit()
        }else{
            fragmentTransaction.commitAllowingStateLoss()
        }
        return true
    }

    private fun showImageChooserDialog() {
        val items = arrayOf(
            resources.getString(R.string.takephoto),
            resources.getString(R.string.choosefromgallery),
            resources.getString(R.string.cancel)
        )

        val builder = AlertDialog.Builder(this)
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == resources.getString(R.string.takephoto) -> takePicture()
                items[item] == resources.getString(R.string.choosefromgallery) -> pickImageSingle()
                items[item] == resources.getString(R.string.cancel)-> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun takePicture() {
        cameraPicker = CameraImagePicker(this)
        cameraPicker!!.setImagePickerCallback(this)
        pickerPath = cameraPicker!!.pickImage()
    }

    private fun pickImageSingle() {
        CropImage.activity()
            .start(this)
    }


    /*@SuppressLint("CheckResult")
    fun captureFromCamera(callback: (responseData: FileData) -> Unit) {
        RxPaparazzo.single(this)
            .usingCamera()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->

                if (response.resultCode() != Activity.RESULT_OK) {
                    response.targetUI()
                }
                if (response.data() != null) {
                    callback(response.data())
                    // loadImage(position, response.data().file.absolutePath)
                }
            }
    }

    @SuppressLint("CheckResult")
    fun pickFromGallary(callback: (responseData: FileData) -> Unit) {
        RxPaparazzo.single(this)
            .usingGallery()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.resultCode() != Activity.RESULT_OK) {
                    response.targetUI()
                }
                if (response.data() != null) {
                    callback(response.data())
                }
            }
    }
*/

    fun manageAppUIMode(appUIMode: Int,context: Context){
        when(appUIMode){
            APP_UI_MODE_DAY -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            APP_UI_MODE_NIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            APP_UI_MODE_AUTO -> {
                val nightModeFlags: Int = context.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK
                when (nightModeFlags) {
                    Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Configuration.UI_MODE_NIGHT_UNDEFINED -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        /*SplashActivity.startActivity(context)
        finishAffinity()*/
    }

    fun checkLocationPermission(): Boolean {

        return viewModel.checkPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    /*fun openLocationSettingDialog(callback: () -> Unit) {
        locationSettingDialog() {
            callback()
        }
        viewModel.requestPermission(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) { isGranted ->
            locationSettingDialog() {
                callback()
            }

        }
    }

    fun locationSettingDialog(callback: () -> Unit) {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        client.checkLocationSettings(builder.build())
            .addOnSuccessListener { _ ->
                callback()
            }
            .addOnFailureListener { exception ->

                when ((exception as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        if (exception is ResolvableApiException) {
                            try {
                                exception.startResolutionForResult(
                                    this,
                                    REQ_CODE_LOCATION_SETTING
                                )
                            } catch (sendEx: IntentSender.SendIntentException) {

                            }
                        }
                    }
                    *//*LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        CommonUtils.showOkDialog(
                            context,
                            message = getString(R.string.location_setting)
                        )
                    }*//*
                }
            }
    }*/

    fun applyLocationPermission(callback: (isGranted: Boolean) -> Unit) {
        viewModel.requestPermission(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) { isGranted ->
//                locationSettingDialog() {
////                      Log.d("TAG", "Success")
////                  startLocationUpdates()
//                callback()
//            }
            callback(isGranted)
        }
    }

    open fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    private var progressDialog: ProgressDialog? = null

    fun showProgressDialog(
        mContext: Context,
        title: String = "",
    ) {
        progressDialog = ProgressDialog(mContext)
        progressDialog?.setCancelable(false)
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            } else {
                it.show()
            }
        }
    }

    fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun onError(p0: String?) {

    }

    override fun onImagesChosen(p0: MutableList<ChosenImage>?) {
        if (p0!!.isNotEmpty()) {
            CropImage.activity(Uri.parse(p0[0].queryUri))
                .start(this)
        }
    }

}