package koderlabs.com.screenshotnotifier

/**
 * This class is used to observe the screenShot event triggered and notify the user using LiveData
 *
 * @author
 * Anum Shafiq
 * Koderlabs
 * Kotlin Android Developer
 */

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import java.io.File

class ScreenShotContentObserver(handler: Handler,
                                private val context: Context?) : ContentObserver(handler) {

    private var isFromEdit = false
    private var previousPath: String? = null
    val screenShot: MutableLiveData<Boolean> = MutableLiveData()


    override fun onChange(selfChange: Boolean, uri: Uri) {
        var cursor: Cursor? = null
        try {
            cursor = context?.contentResolver?.query(
                    uri,
                    arrayOf(MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.DATA),
                    null,
                    null,
                    null
            )
            if (cursor != null && cursor.moveToLast()) {
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val path = cursor.getString(dataColumnIndex)
                if (File(path).lastModified() >= System.currentTimeMillis() - 10000) {
                    if (isScreenshot(path) && !isFromEdit && !(previousPath != null && previousPath == path)) {
//                        Timber.i("screenShot Live Event called")
                        screenShot.value = true
                    }
                    previousPath = path
                    isFromEdit = false
                } else {
                    cursor.close()
                    return
                }
            }
        } catch (t: Throwable) {
            isFromEdit = true
        } finally {
            cursor?.close()
        }
        super.onChange(selfChange, uri)
    }

    private fun isScreenshot(path: String?): Boolean {
        return path != null && path.toLowerCase().contains("screenshot")
    }
}