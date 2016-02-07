# Project 1 - InstagramClient

Instagram is an android app that allows a user to check out popular photos from Instagram. The app utilizes the Instagram API to display images and basic image information to the user.

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current popular photos** from Instagram
* [X] For each photo displayed, user can see the following details:
  * [X] Graphic, Caption, Username
  * [X] Relative timestamp, like count, user profile image

The following **optional** features are implemented:

* [X] User can **pull-to-refresh** popular stream to get the latest popular photos
* [X] Show latest comments for each photo
* [X] Display each photo with the same style and proportions as the real Instagram
* [X] Display each user profile image using a RoundedImageViewDisplay each user profile image using a [RoundedImageView](https://github.com/vinc3m1/RoundedImageView)
* [X] Display a nice default placeholder graphic for each image during loading
* [X] Improved the user interface through styling and coloring

The following **bonus** features are implemented:

* [X] Show last 2 comments for each photo
* [ ] Allow user to view all comments for an image within a separate activity or dialog fragment
* [X] Allow video posts to be played in full-screen using the VideoView

The following **additional** features are implemented:

* [X] Apply the Butterknife annotation library to reduce view boilerplate. 
* [X] Use GSON to create the models from the JSON response instead of manually parsing the JSON
* [X] On cold launch, display the refresh spinner while fetching popular instagram posts

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='![instagram](https://cloud.githubusercontent.com/assets/1521460/12875699/902372da-cda4-11e5-8128-173cfd917d57.gif)
' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Butterknife](https://github.com/JakeWharton/butterknife) - reduce view boilerplate
- [GSON](https://github.com/google/gson) - Java serialization library for converting Java objects to JSON
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A fast circular image view used for profile pictures


## License

    Copyright 2016 Scott MacGregor

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
