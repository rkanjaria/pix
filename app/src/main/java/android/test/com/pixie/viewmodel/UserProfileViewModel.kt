package android.test.com.pixie.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.test.com.pixie.models.Image
import android.test.com.pixie.repositories.UserProfileRepository
import io.reactivex.disposables.CompositeDisposable

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userProfileRepository: UserProfileRepository
    private val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
        userProfileRepository = UserProfileRepository(compositeDisposable)
    }

    fun getUserClickedImages(userName: String): MutableLiveData<List<Image>>? {
        return userProfileRepository.getUserClickedImages(userName)
    }
}