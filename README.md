# EcoShop
EcoShop is a sample Android project using [Fake Store API](https://fakestoreapi.com/) based on MVVM architecture. It showcases the latest Android tech stacks with well-designed architecture and best practices

# Screen Shot
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_001832.png"  width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002221.png"  width="200" height="400" /> | 
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002249.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002308.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002319.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002330.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002408.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002418.png" width="200" height="400" /> |
<img src="https://github.com/faridsolgi/EcoShop/blob/master/screenShots/Screenshot_20230502_002438.png" width="200" height="400" /> 


# Features
100% Kotlin

MVVM architecture

Android Architecture Components

Kotlin Coroutines + Flow

Single activity pattern

Dependency injection with DAGGER HILT



# Tech Stacks
[Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/) - RESTful API and networking client.

[Hilt](https://dagger.dev/hilt/)  - Dependency injection.

[ViewBinding](https://developer.android.com/topic/libraries/view-binding) - View binding is a feature that allows you to more easily write code that interacts with views 

[Android Architecture Components](https://developer.android.com/topic/libraries/architecture)  - A collections of libraries that help you design rebust, testable and maintainable apps.

[ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel)  - UI related data holder, lifecycle aware.

[Navigation component](https://developer.android.com/guide/navigation)  -  Fragment routing handler

[Room ORM](https://developer.android.com/training/data-storage/room)  - Database object mapping library use to access the database

[Coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency design pattern for asynchronous programming.

[Flow](https://developer.android.com/kotlin/flow) - Stream of value that returns from suspend function.

[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - Android chart view / graph view library.

[Picasso](https://square.github.io/picasso/) - Image loading

[Material Theme Builder](https://m3.material.io/theme-builder#/dynamic) - generate theme and colors 



# Architectures
![alt - Github](https://raw.githubusercontent.com/amitshekhariitbhu/MVVM-Architecture-Android/master/assets/mvvm-arch.png)

**View** : Activity/Fragment with UI-specific logics only.

**ViewModel** : It keeps the logic away from View layer, provides data streams for UI and handle user interactions

**Model** :  Repository pattern, data layers that provide interface to manipulate data from both the local and remote data sources. The local data sources will serve as single source of truth
