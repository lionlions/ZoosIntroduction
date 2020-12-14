# ZoosIntroduction
For 國泰數數發面試作業

此專案為國泰數數發面試作業

### 主要架構
1. MVVM (LiveData + dataBinding)
2. Jetpack Navigation
   (Navigation drawer + Toolbar + NavController)

### 使用套件
1. MotionLayout
2. Glide
3. Firebase
4. Gson
5. Retrofit

### 認為加分項目
1. 因動物園管區介紹並無Api, 因此將資料放進 Firebase Firestore 進行資料串接
2. 使用 MotionLayout 使畫面看起來平滑舒服

### 遇到疑惑的地方
1. 植物介紹的Api，加上 limit 跟 offset 時和未加上時的結果似乎不太一樣
2. 植物介紹的Api，在 results 中會發現其中兩個 key值是: "﻿F_Name_Ch" 和 "F_Function＆Application" 有特殊字元在裡面，所以無法直接用 Retrofit + Gson 的方式完成，改用 Retrofit 呼叫 Api 後回傳 String 將其置換成 "F_Name_Ch" 和 "F_Function_Application" 後再利用 Gson 包裝成物件
