package android.test.com.pixie.widgets

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.support.v4.content.ContextCompat
import android.test.com.pix.R
import android.util.AttributeSet
import android.widget.TextView

class GradientTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TextView(context, attrs, defStyle) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.setShader(LinearGradient(0f, 0f,
                    width.toFloat(),
                    height.toFloat(),
                    ContextCompat.getColor(context, R.color.colorFront),
                    ContextCompat.getColor(context, R.color.colorBack),
                    Shader.TileMode.CLAMP))
        }
    }
}