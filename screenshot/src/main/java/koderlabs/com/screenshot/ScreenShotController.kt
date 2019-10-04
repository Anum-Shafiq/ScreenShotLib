package koderlabs.com.screenshotnotifier

/**
 * This is the library designed to notify the user about screenshot.
 * If the application permits READ_EXTERNAL_STORAGE then user will be notified whenever ScreeShot captured
 * else library will make the window secure or in other words screenshot will not be captured.
 *
 * @author
 * Anum Shafiq
 * Koderlabs
 * Kotlin Android Developer
 */

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData

/**
 *  To use the library you are requested to pass the application context and your MainActivity instance
 */
class ScreenShotController constructor(private val context: Context){

    /**
     * handler and screenShot content observer instances to pass to the basic Screen Shot Observer
     */
    private val handler = Handler()
    private val screenShotContentObserver by lazy {
        ScreenShotContentObserver(
                context = context, handler = handler)
    }

    /**
     * fun to register the observer with the given widow and context
     */
    open fun registerObserver(window: Window) {
        val permissionCheck = this.let {
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            context.contentResolver.registerContentObserver(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    true,
                    screenShotContentObserver
            )
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    /**
     * fun to unregister the observer on activity ondestory()
     */
    open fun unregisterObserver() {
        try {
            context.contentResolver.unregisterContentObserver(screenShotContentObserver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun observerScreenShot(): MutableLiveData<Boolean> = screenShotContentObserver.screenShot
}