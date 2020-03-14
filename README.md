# ReignTest

This is the Apply Test for Reign as a Kotlin Android developer
The App is just an API Rest consumer that get so call Stories and show then into a RecyclerView

The App allow to perform swipe left to delete and swipe down to refresh, also when you open a Story it send you to the Url inside a WebView.

The App has:
- targetSDKVersion of 29
- minSDKVersion of 22
- compiledSDKVersion of 29

This App use some estra libraries as:

- GSON
- Volley
- SwipeRefreshLayout from AndroidX
- RecyclerView from AndroidX

Version 1.1

- Implementation of Realm and deleted SQlite
- added Toast messages for user to know what can and cannot do according to InternetConnection.

*The App remeber what Stories you deleted, please if you delete all the Stories just erase the Data/Cache of the App.
*Please, Remember that some new Smartphones has the right to keep data (like Databases) even if you delete the App, so 
you have to disable that option in your Configuration Menu in case that you cannot delete the database erasing Data/Cache.


