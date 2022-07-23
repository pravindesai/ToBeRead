package com.onebitcompany.toberead.common

object Constants {
    const val BASE_URL: String = "https://page-book-ten.hasura.app/v1/graphql"
    const val HEADER_KEY: String = "x-hasura-admin-secret"
    const val HEADER_VAL: String =
        "k7reuKqhRXSynqkQzB9Q6pnwihvS00rdpUEb3mEexPxMRLG1J86VVj3dAL2QWl5d"
    const val CACHE_FILE_NAME: String = "apollo_chache_file"
    const val CACHE_FILE_SIZE: Long = 1024 * 1024

    val KEY_SHARED_PREF_FILE = "sharedPreferenceFile"
    val IS_GUEST = "is_guest"

    const val SPLASH_SCREEN = "splash_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val HOME_SCREEN = "home_screen"
    const val BOOK_LIST_SCREEN = "book_list_screen"
    const val SETTINGS_SCREEN = "settings_screen"

    const val LOGIN_ROUTE = "login_route"
    const val DASHBOARD_ROUTE = "dashboard_route"
    const val ROOT_ROUTE = "root_route"

    //strings
    const val BOOK_NAME = "ToBeRead"
    const val GUEST = "Login As Guest"
    const val GOOGLE = "Join with Google"
    const val FACEBOOK = "Join with Facebook"

}