
# ScreenShotLib

Library to observe the screen shot captured in the project

ScreenShot Library is all about the user privacy you can add this library to your project and get notified when a screen shot is captured. ScreenShotLib uses android.permission.WRITE_EXTERNAL_STORAGE. If the permission is denied then user will not be able to capture the screen shot.

# Gradle Dependency

Firstly Add Jitpack to your repositories.

```gradle
allprojects {
	repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add ScreenShotLib to your app module's `build.gradle` file:

```gradle
dependencies {
	implementation 'com.github.Anum-shafiq:ScreenShotLib:Tag'
	}
```

# Implementation

Firstly, initiate the screenShotController variable then register observer in the application either from fragment or activity as follow:

```kotlin
//ScreenShotController Class is the base class for the library
private val screenShotController by lazy { ScreenShotController(this) }

//Register observer
override fun onResume() {
	screenShotController.registerObserver(this.window)
    super.onResume()
    }
```
  
Add following code to observe the screenShot captured event wherever required
  
```kotlin
//observerScreenShot is the LiveData that is being changed on Screen Shot capture
screenShotController.observerScreenShot().observe(this, Observer {
	it?.let { Toast.makeText(this, "You can do notifying here", Toast.LENGTH_LONG).show() }
	})
```

Finally, don't forget to unregister the oberver using the code provided

```kotlin
//Unregister observer 
override fun onStop() {
	screenShotController.unregisterObserver()
    super.onStop()
	}
```

License
-------

Copyright 2017 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
