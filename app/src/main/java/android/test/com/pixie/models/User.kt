package android.test.com.pixie.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("twitter_username") val twitterUsername: String?,
        @SerializedName("instagram_username") val instagramUrl: String?,
        @SerializedName("links") val links: Links?,
        @SerializedName("profile_image") val profileImage: Profile?)

data class Links(@SerializedName("id") val id: String?,
                 @SerializedName("username") val username: String?,
                 @SerializedName("name") val name: String?,
                 @SerializedName("twitter_username") val twitterUsername: String?)

data class Profile(@SerializedName("id") val id: String?,
                   @SerializedName("username") val username: String?,
                   @SerializedName("name") val name: String?,
                   @SerializedName("twitter_username") val twitterUsername: String?)

 /*       "user": {
    "id": "nuKDH32RDaA",
    "updated_at": "2018-12-07T09:24:12-05:00",
    "username": "dhivakrishna",
    "name": "Dhiva Krishna",
    "first_name": "Dhiva",
    "last_name": "Krishna",
    "twitter_username": null,
    "portfolio_url": null,
    "bio": null,
    "location": null,
    "links": {
        "self": "https://api.unsplash.com/users/dhivakrishna",
        "html": "https://unsplash.com/@dhivakrishna",
        "photos": "https://api.unsplash.com/users/dhivakrishna/photos",
        "likes": "https://api.unsplash.com/users/dhivakrishna/likes",
        "portfolio": "https://api.unsplash.com/users/dhivakrishna/portfolio",
        "following": "https://api.unsplash.com/users/dhivakrishna/following",
        "followers": "https://api.unsplash.com/users/dhivakrishna/followers"
    },
    "profile_image": {
        "small": "https://images.unsplash.com/profile-1510002111157-5d266275091d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32",
        "medium": "https://images.unsplash.com/profile-1510002111157-5d266275091d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64",
        "large": "https://images.unsplash.com/profile-1510002111157-5d266275091d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128"
    },
    "instagram_username": "brownguytakesphotos",
    "total_collections": 0,
    "total_likes": 1,
    "total_photos": 8,
    "accepted_tos": false
},*/