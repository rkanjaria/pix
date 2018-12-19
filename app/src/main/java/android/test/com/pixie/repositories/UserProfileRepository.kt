package android.test.com.pixie.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.test.com.pixie.helpers.RetrofitHelper
import android.test.com.pixie.models.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserProfileRepository(val compositeDisposable: CompositeDisposable) {

    private val _userImagesList = MutableLiveData<List<Image>>()
    val userImagesList: LiveData<List<Image>> = _userImagesList

    fun getUserClickedImages(userName: String) : LiveData<List<Image>> {
        compositeDisposable.add(RetrofitHelper.create().getUserClickedPhotos(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { imageList, error ->
                    _userImagesList.postValue(imageList)
                })

        return userImagesList
    }
}