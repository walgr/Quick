## 乂架构

[![](https://jitpack.io/v/walgr/Quick.svg)](https://jitpack.io/#walgr/Quick)

### 项目目的

    简化代码，争取做到最小代码实现功能。

    PS：R2部分逻辑参照butterknife项目，侧滑参照SwipeMemulayout项目重构

    项目目前处于初期建设阶段，文档粗略，功能可参照app里的demo。

    有好想法欢迎提issue，我来实现。

### 项目功能

1.统一Activity、Fragment（提供能力支持ViewModel, ViewDataBinding）
    
2.类似ButterKnife的功能注解
    
3.可定制宽高、动画的Dialog，DialogFragment
    
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
        依赖全部
        implementation 'com.github.walgr.Quick:QuickWork:latest'

        看需求依赖
        implementation 'com.github.walgr.Quick:QuickUtil:latest'
        implementation 'com.github.walgr.Quick:QuickNetwork:latest'
        implementation 'com.github.walgr.Quick:QuickDialog:latest'
        implementation 'com.github.walgr.Quick:QuickRecyclerView:latest'
        implementation 'com.github.walgr.Quick:QuickWidget:latest'
    }

### 未来功能

    1.父选择基类支持悬浮和展开收起功能 已实现

    2.BindFragment支持ViewPager2 已实现

    3.可刷新List支持请求接口后自动加载 已实现
    
    4.布局View支持请求接口后自动映射赋值 已实现

    5.布局xml支持接口请求并自动赋值 已实现

    6.列表可定义上中下边距 已实现
    
    7.布局xml可嵌套功能组件 已实现

    8.弹窗自适应内容高度 已实现

    9.接口请求可绑定页面生命周期 已实现

    10.列表集成错误页

    11.列表支持上下拖动 能力已实现

    12.view数据自动同步（无需手动通知修改） 框架已实现，待完善主流view

    13.Dialog优先级管理器 已实现

    14.列表支持侧滑 能力已实现
