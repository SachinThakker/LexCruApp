package com.lexcru.lexcruapp.utils



/*Request Codes*/
const val REQ_CODE_GOOGLE_LOGIN = 1001
const val REQ_CODE_ADD_BOOK_REVIEW = 1002
const val REQ_CODE_ADD_BOOK_REVIEW_FROM_BOOK_DETAIL = 1003
const val REQ_CODE_IN_PLAYBOOK = 1004

const val HTTP_SUCCESS = 200
const val UNAUTHORISED = 203
const val HTTP_BAD_REQUEST = 201
const val HTTP_UNAUTHORIZED = 203
const val HTTP_NO_DATA_FOUND = 204
const val HTTP_NOT_FOUND = 404
const val HTTP_FAILURE = 400
const val HTTP_INTERNAL_SERVER_ERROR = 500
const val HTTP_TOKEN_INVALID = 401

/*API Parameters*/
const val REGISTRATION_SIMPLE = 1
const val REGISTRATION_FACEBOOK = 2
const val REGISTRATION_GOOGLE = 3
const val DEVICE_TYPE_ANDROID = 2

const val VIEW_ITEM = 0
const val VIEW_LOADING = 1
const val FORMAT = "%02d:%02d"

const val READ_MORE_LINES = 3


const val PREF_LOGIN_DATA = "pref_login_data"
const val PREF_APP_UI_MODE = "pref_app_ui_mode"
const val PREF_APP_LANGUAGE = "pref_app_language"
const val PREF_FCM_TOKEN = "pref_fcm_token"

/*Intent Extras Parameters*/
const val INTENT_EXTRA_RECEIVED_OTP = "intent_extra_received_otp"
const val INTENT_EXTRA_SIGNUP_DETAILS = "intent_extra_signup_details"
const val INTENT_REGISTER_TYPE = "intent_extra_register_type"
const val INTENT_EXTRA_CATEGORY = "intent_extra_category"
const val INTENT_EXTRA_CATEGORY_ID = "intent_extra_category_id"
const val INTENT_EXTRA_BOOK_ID = "intent_extra_book_id"
const val INTENT_EXTRA_BOOK_NAME = "intent_extra_book_name"
const val INTENT_EXTRA_BOOK_AUTHOR = "intent_extra_book_author"
const val INTENT_EXTRA_IS_FROM_SEARCH = "intent_extra_is_from_search"
const val INTENT_EXTRA_IS_FROM_CATEGORY_SEVEN = "intent_extra_is_from_category_seven"
const val INTENT_EXTRA_SEARCH_TAB_ID = "intent_extra_search_tab_id"
const val INTENT_EXTRA_PODCAST_ID = "intent_extra_podcast_id"
const val INTENT_EXTRA_PODCAST_NAME = "intent_extra_podcast_name"
const val INTENT_EXTRA_PODCAST_AUTHOR = "intent_extra_podcast_author"
const val INTENT_EXTRA_IS_REVIEWED = "intent_extra_is_reviewed"
const val INTENT_EXTRA_OVERALL_RATING = "intent_extra_overall_rating"


/*App UI Modes*/
const val APP_UI_MODE_DAY = 0
const val APP_UI_MODE_NIGHT = 1
const val APP_UI_MODE_AUTO = 2
