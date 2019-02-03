package android.test.com.pixie.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import android.view.View

class BlurBackground {

    companion object {

        private val BITMAP_SCALE = 0.4f
        private val BLUR_RADIUS = 7.5f

        fun blur(view: View): Bitmap {
            return blur(view.context, getScreenShot(view))
        }

        fun blur(context: Context, image: Bitmap): Bitmap {
            val width = Math.round(image.width * BITMAP_SCALE)
            val height = Math.round(image.height * BITMAP_SCALE)

            val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
            val outputBitmap = Bitmap.createBitmap(inputBitmap)

            val renderScript = RenderScript.create(context)
            val instrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            val tempIn = Allocation.createFromBitmap(renderScript, inputBitmap)
            val tempOut = Allocation.createFromBitmap(renderScript, outputBitmap)
            with(instrinsic) {
                setRadius(BLUR_RADIUS)
                setInput(tempIn)
                forEach(tempOut)
            }
            tempOut.copyTo(outputBitmap)
            return outputBitmap
        }

        fun getScreenShot(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
    }
}