# Dribbble Android App
[![Build Status](https://travis-ci.org/AvalonExcalibur/DribbbleApp.svg?branch=master)](https://travis-ci.org/AvalonExcalibur/DribbbleApp)
[![Apache Licence](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0.html)

使用Dribbble API 设计的一个Dribbble客户端

### 日程

- 2016-08-06 添加了模板代码和基本的 OAuth 2.0 WebFlow验证操作
- 2016-08-07 添加了对首页的简单MVP构架，并尝试调用Dribbble User API
- 2016-08-10 重建MVP+Clean架构，引入Dagger2和Interactor（UseCase）并查询到部分API用法
- 2016-08-11 根据最初完成的Popular模块模板构建了其他页面（#BUG没有对多线程和RxJava进行细化和优化，所以快速切换情况下应用会崩溃）
  使用了Fabric作为crash分析组件
  由于Fresco是支持加载gif图片的，同时对于在列表（RecyclerView）中多线程加载图片有优化，所以没有特殊依赖别的实现和进行优化

- 2016-08-15 完成了一些基本功能（查看shot，like，查看评论）
- 2016-08-16 添加了数据源，去除了评论功能


### 预览
![](/art/device-2016-08-16-234112.png)
![](/art/device-2016-08-16-234144.png)
![](/art/device-2016-08-16-234213.png)
![](/art/device-2016-08-16-234241.png)
![](/art/device-2016-08-16-234317.png)
![](/art/device-2016-08-16-234412.png)


### Licence

> Copyright (c) 2016 lhyz Android Open Source Project
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>     http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.