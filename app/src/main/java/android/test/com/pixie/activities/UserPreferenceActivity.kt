package android.test.com.pixie.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.test.com.pix.R

class UserPreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preference)
    }
}
