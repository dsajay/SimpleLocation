# SimpleLocation

How to get a Git project into your build:

Step 1. Add the JitPack repository to your build file


Add it to your root build. gradle at the end of repositories:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


Step 2. Add the dependency

dependencies {
	        implementation 'com.github.dsajay:SimpleLocation:1.0.0'
	}



[![](https://jitpack.io/v/dsajay/SimpleLocation.svg)](https://jitpack.io/#dsajay/SimpleLocation)
