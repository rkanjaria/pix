package android.test.com.pixie.repositories

import android.arch.lifecycle.MutableLiveData
import android.test.com.pixie.helpers.RetrofitHelper
import android.test.com.pixie.models.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserProfileRepository(val compositeDisposable: CompositeDisposable) {

    val userImagesList: MutableLiveData<List<Image>>? = null

    fun getUserClickedImages(userName: String): MutableLiveData<List<Image>>? {
        compositeDisposable.add(RetrofitHelper.create().getUserClickedPhotos(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    userImagesList?.value = it
                })

        return userImagesList
    }
}