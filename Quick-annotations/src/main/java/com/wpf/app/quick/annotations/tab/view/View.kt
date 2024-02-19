package com.wpf.app.quick.annotations.tab.view

enum class ViewType(val packageName: String, val className: String) {
    //系统
    View("android.view", "View"),
    Space("android.view", "Space"),
    TextView("android.widget", "TextView"),
    Button("android.widget", "Button"),
    EditView("android.widget", "EditView"),
    CheckBox("android.widget", "CheckBox"),
    RadioGroup("android.widget", "RadioGroup"),
    RadioButton("android.widget", "RadioButton"),
    ImageView("android.widget", "ImageView"),
    ImageButton("android.widget", "ImageButton"),
    ProgressBar("android.widget", "ProgressBar"),
    RatingBar("android.widget", "RatingBar"),
    SeekBar("android.widget", "SeekBar"),
    Spinner("android.widget", "Spinner"),
    Switch("android.widget", "Switch"),
    TimePicker("android.widget", "TimePicker"),
    VideoView("android.widget", "VideoView"),
    ScrollView("android.widget", "ScrollView"),
    HorizontalScrollView("android.widget", "HorizontalScrollView"),
    LinearLayout("android.widget", "LinearLayout"),
    RelativeLayout("android.widget", "RelativeLayout"),
    FrameLayout("android.widget", "FrameLayout"),
    ConstraintLayout("androidx.constraintlayout.widget", "ConstraintLayout"),
    GridLayout("android.widget", "GridLayout"),
    RecyclerView("androidx.recyclerview.widget", "RecyclerView"),
    WebView("android.webkit", "WebView"),

    //第三方
    LottieView("com.airbnb.lottie", "LottieAnimationView"),
}
