## 乂架构

[![](https://jitpack.io/v/walgr/Quick.svg)](https://jitpack.io/#walgr/Quick)

### 项目目的

    简化代码，争取做到最小代码实现功能。

    PS：R2部分逻辑参照butterknife项目

### 项目功能

1.基础Activity、ViewModelActivity、ViewBindingActivity、Fragment
    
2.类似ButterKnife的功能注解
    
3.可定制宽高，动画的Dialog、DialogFragment
    
4.快速搭建列表 
![RecyclerView.png](./assets/README/README-1658197283964.png)

5.快速集成retrofit-okhttp网络请求，便捷网络请求

### 项目集成

    1.添加远程地址
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    
    2.添加依赖
    dependencies {
        implementation 'com.github.walgr.Quick:Quick:latest'
        implementation 'com.github.walgr.Quick:QuickNetwork:latest'
    }
