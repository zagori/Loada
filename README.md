
# Loada
<a href="https://github.com/zagori/Loada/blob/master/attachments/loada.aar?raw=true"><img src="https://api.bintray.com/packages/zagori/maven/com.zagori:mediaviewer/images/download.svg?version=1.0.0"/></a>
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Overview
A library that allows downloading and in-memory caching of files. It has a ready-to-use bitmap, JSON and bytes downloaders.  

* Performs asynchronous HTTP Request to download files of any data type and return it as byte[]. You may parse it to any other data type.
* Includes ready-to-use JSONArray and bitmap downloaders. It can easily add support for String and JSONObject data types.
* Has efficient in-memory cache, which is configurable and set to a default size (runtime max memory /8). Disk cache can be easily added.
* Any on-going download request can be cancelled.
* For now, Loada set request method by default to GET, but it can be easily extended to include POST.


# Setup
#### Step 1: Download loada.aar
<a href="https://github.com/zagori/Loada/blob/master/attachments/loada.aar?raw=true"><img src="https://api.bintray.com/packages/zagori/maven/com.zagori:mediaviewer/images/download.svg?version=1.0.0"/></a>

#### Step 2: Import Loada library to you project
1. On Android Studio, go to **File > New > New Module**.
2. Select **Import .JAR/.AAR Package**.
3. Specify the location of the AAR file, then click finish.
4. In the build.gradle of your app, add loada library to the dependencies.
```
dependencies{
  implementation project(':loada')
}
```
# Screenshots
<img src="https://github.com/zagori/Loada/blob/master/attachments/Screenshot.gif?raw=true" width="250">

# Usage
### Download image
Image can be downloaded in 2 ways: 
- Pass url as string, an ImageView and errorListener, then Loada will set the image to imageView.
- Pass url as string and a imageListener, then Loada will return a succes load as bitmap or an error.

```Java
Loada.get(context).loadImage(url, imageView,
                new ImageErrorListener() {
                    @Override
                    public void onError(Throwable error) {
                        // do something with error
                    }
                });
```

```Java
Loada.get(context).loadImage(url,
                new ImageListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        // set something with bitmap
                    }
                    
                    @Override
                    public void onError(Throwable error) {
                        // do something with error
                    }
                });
```

### Download JSONArray
``` Java
Loada.get(context).loadJsonArray(url, new JsonArrayListener() {
            @Override
            public void onSuccess(JSONArray result) {
                // Do something with result
            }

            @Override
            public void onError(ResponseError error) {
                // Do something with error
            }
        });
```

### Download Raw Data
Load allows to download any data type and returns it as byte[], which can be parsed to any file type.
``` Java
Loada.get(context).loadRawData(url, new BytesListener() {
            @Override
            public void onSuccess(byte[] result) {
                // Do something with result
            }

            @Override
            public void onError(ResponseError error) {
                // Do something with error
            }
        });
```

### Cancel Request
Any on-going request can be cancelled by passing the tag of that request. By default, the tag is the URL used to fetch the data.
```Java
Loada.get(context).cancelDownloadRequest(url);
``` 

# Developer
* [LinkedIn](https://www.linkedin.com/in/yousseflabihi/).
* [Twitter](https://twitter.com/yourizagori).


# License
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
```
Copyright 2019 Zagori

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
