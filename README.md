

# ![](D:\Downloads\reload (1).png)Animator Progress Bar for Android

[![Preview](https://img.youtube.com/vi/YOUTUBE_VIDEO_ID_HERE/0.jpg)](https://www.youtube.com/watch?v=mcMnOQ4oLgU&feature=youtu.be) 



**Gradle build**

```groovy
compile 'in.blackpaper.animatorprogress:animatorprogress:1.0.0'
```



**Maven build settings (ver: 1.0.0)**

```<dependency>
  <groupId>in.blackpaper.animatorprogress</groupId>
  <artifactId>animatorprogress</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

__Usages__

```Xml
<in.blackpaper.animatorprogress.AnimatorView
    android:id="@+id/animator_view"
    style="@style/ProgressReporter_Default"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginLeft="10dp"
    android:layout_marginRight="10dp"
    android:background="@drawable/ic_box"
    android:gravity="center"
    app:while_progress_text="Downloading..."></in.blackpaper.animatorprogress.AnimatorView>
```

__Buy me a coffee__

[![ko-fi](https://www.ko-fi.com/img/donate_sm.png)](https://ko-fi.com/H2H2HPSR)

__Additional attributes__

```Xml
<attr name="pre_drawable" format="reference" />
<attr name="post_drawable" format="reference" />
<attr name="animated_drawable" format="reference" />

<attr name="onCompleted_text" format="string|reference" />
<attr name="while_progress_text" format="string|reference" />
<attr name="process_title" format="string|reference" />
<attr name="onCompleted_text_color" format="color|reference" />
<attr name="while_progress_text_color" format="color|reference" />
<attr name="process_title_color" format="color|reference" />
<attr name="show_progress" format="boolean" />
<attr name="progress_prefix" format="string|reference" />
<attr name="progress_suffix" format="string|reference" />
<attr name="progress_prefix_color" format="color|reference" />
<attr name="progress_suffix_color" format="color|reference" />
<attr name="current_progress_text_color" format="color|reference" />


<attr name="progress_current" format="integer" />
<attr name="progress_max" format="integer" />
<attr name="progress_unreached_color" format="color|reference" />
<attr name="progress_reached_color" format="color|reference" />
<attr name="progress_reached_bar_height" format="dimension" />
<attr name="progress_unreached_bar_height" format="dimension" />
<attr name="progress_text_size" format="dimension" />
<attr name="progress_text_color" format="color" />
<attr name="progress_reachedtext_rightradius" format="dimension" />
<attr name="progress_reachedtext_leftradius" format="dimension" />
<attr name="progress_unreachedtext_rightradius" format="dimension" />
<attr name="progress_unreachedtext_leftradius" format="dimension" />
<attr name="progress_text_offset" format="dimension" />
```

**Preview Screenshots**

![1](D:\Animatorprogreebar\1.png)



![2](D:\Animatorprogreebar\2.png)

![3](D:\Animatorprogreebar\3.png)

### License

```Copyright 2018 Nitin Khanna
Copyright 2018 Nitin Khanna

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```