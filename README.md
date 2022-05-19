# 乂架构
[![](https://jitpack.io/v/walgr/Quick.svg)](https://jitpack.io/#walgr/Quick)

### Android Studio 环境配置
    1.预览MarkDown
        1.允许JCEF
            点击Help-Find Action...
            输入Choose Boot Runtime for the IDE
            弹窗里选择带有JCEF的jdk版本
        2.安装MarkDown Editor插件


### 项目目的
    简化代码，争取做到最小代码实现功能。

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
	        implementation 'com.github.walgr:Quick:0.0.1'
	}
