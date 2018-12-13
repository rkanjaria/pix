package android.test.com.pixie.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("twitter_username") val twitterUsername: String?,
        @SerializedName("instagram_username") val instagramUrl: String?,
        @SerializedName("links") val links: Links?,
        @SerializedName("profile_image") val profileImage: Profile?): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Links::class.java.classLoader),
            parcel.readParcelable(Profile::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(twitterUsername)
        parcel.writeString(instagramUrl)
        parcel.writeParcelable(links, flags)
        parcel.writeParcelable(profileImage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

data class Links(@SerializedName("self") val selfUrl: String?,
                 @SerializedName("html") val htmlUrl: String?,
                 @SerializedName("photos") val photosUrl: String?,
                 @SerializedName("likes") val likesUrl: String?,
                 @SerializedName("portfolio") val portfolioUrl: String?,
                 @SerializedName("following") val followingUrl: String?,
                 @SerializedName("followers") val followersUrl: String?): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(selfUrl)
        parcel.writeString(htmlUrl)
        parcel.writeString(photosUrl)
        parcel.writeString(likesUrl)
        parcel.writeString(portfolioUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(followersUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }
}

data class Profile(@SerializedName("small") val small: String?,
                   @SerializedName("medium") val medium: String?,
                   @SerializedName("large") val large: String?): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(small)
        parcel.writeString(medium)
        parcel.writeString(large)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profile> {
        override fun createFromParcel(parcel: Parcel): Profile {
            return Profile(parcel)
        }

        override fun newArray(size: Int): Array<Profile?> {
            return arrayOfNulls(size)
        }
    }
}