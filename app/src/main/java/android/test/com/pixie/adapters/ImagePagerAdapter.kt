package android.test.com.pixie.adapters

import android.support.v4.view.PagerAdapter
import android.view.View

class ImagePagerAdapter: PagerAdapter() {
    override fun isViewFromObject(view: View, p1: Any): Boolean {

    }

    override fun getCount(): Int {
        return 10
    }
}