package android.test.com.pixie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    fun getUserClickedImages(userName: String): LiveData<List<Image>>? {
        return userProfileRepository.getUserClickedImages(userName)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}