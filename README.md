# NumberCounterView

![NumberCounterView](https://i.ibb.co/7X5BBsr/Screen-Shot-2021-09-11-at-11-36-29.png)

## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
 }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:numbercounterview:latest'
}
```
# Usage

The NumberCounterView is very easy to use. Just add it to your layout like any other view.
##### Via XML

Here's a basic implementation.

```xml
    <id.co.edtslib.numbercountervie.NumberCounterView
        android:id="@+id/numberCounterView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/clProduct"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

### Attributes information

##### _app:numberCounterValue_
[integer]: default value of counter view, default 1

##### _app:numberCounterMin_
[integer]: minimum value of counter view, default 0

##### _app:numberCounterMax_
[integer]: maximum value of counter view, default Int.MAX_VALUE

##### _app:numberCounterStep_
[integer]: increment/decrement step value of counter view, default 1

### Method
You can set counter view value with this method

```kotlin
    fun setValue(value: Int)
```

### Listening for NumberCounteView

You can set a listener to be notified when user change value. An example is shown below.

```kotlin
    binding.numberCounterView.delegate = object : NumberCounterDelegate {
        override fun onChangeValue(value: Int) {
            TODO("Not yet implemented")
        }
    }
```





