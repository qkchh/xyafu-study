# 学习信农APP

## 效果展示

| <img src="images\Screenshot_20211130_163142_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163142_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163154_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163154_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163627_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163627_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="images\Screenshot_20211130_163417_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163417_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163401_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163401_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163339_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163339_uni.UNID6348DE" style="zoom:25%;" /> |
| <img src="images\Screenshot_20211130_163306_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163306_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163618_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163618_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163410_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163410_uni.UNID6348DE" style="zoom:25%;" /> |

## 开发以及运行环境

- IDEA 2020.3.2
- JDK 1.8
- MySql 5.5
- Android 8以及以上版本

## 使用框架以及服务

前端：uni-app vue.js框架

后端：spring boot + mybatis plus框架

使用服务：阿里云OSS对象存储服务，阿里云虚拟机服务器（后端项目部署在服务器上）

##  主要功能

1. 资讯的浏览，点赞，收藏，评论，搜索以及分类查询。
2. 学习内容的浏览，记录
3. 图书简单的介绍
4. 个人主页信息的编辑，密码的修改，个人点赞，收藏，评论相关内容
5. 账号的注册，登录，退出
6. APP相关内容的展示

## 项目开发过程

###  数据库设计

本系统在设计数据库时需要12个表来实现。下图为表的ER图。

![image-20211130170541078](C:\Users\大坏蛋\AppData\Roaming\Typora\typora-user-images\image-20211130170541078.png)

## 具体功能

### 首页主要功能

#### 首页数据的加载和渲染以及搜索页面的功能介绍

进入首页会自动价值数据到界面。下拉可以刷新数据。然后点击搜索就可以跳转搜索界面。搜索界面有一些热门带选项。可以输入相应的数据进行搜索。点击取下可以回到首页。

| <img src="images\Screenshot_20211130_163142_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163142_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163154_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163154_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163202_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163202_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |

#### 轮播图的展示和内容

轮播图分别是四史，分别点击不同的会进入不同的介绍页面

| <img src="images\Screenshot_20211130_163137_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163137_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163618_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163618_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |



#### 资讯的详情以及点赞功能

点击分类导航可以查看不同分类的资讯内容。

资讯展示方面，会有内容的相应标题，内容的评论数，内容的浏览量和上传时间，和相应的图片信息。

资讯点击后会增加资讯的浏览量，并进入资讯的详情页面。点击评论按钮，可以跳转评论区，查看大家的留言。

点赞收藏功能在用户未登录的情况下不能进行点赞和评论和收藏。点击写评论可以对资讯进行留言，或者对相应评论人的评论进行回复操作。如果评论内容为空还会有相应的提示。

| <img src="images\Screenshot_20211130_163253_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163253_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163301_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163301_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163306_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163306_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="images\Screenshot_20211130_163319_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163319_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_171718_uni.UNID6348DE.jpg" alt="Screenshot_20211130_171718_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_171712_uni.UNID6348DE.jpg" alt="Screenshot_20211130_171712_uni.UNID6348DE" style="zoom:25%;" /> |

### 学习界面功能介绍

如果未登录的话是不能使用学习功能会提示登录，并跳转到登录界面。

一个导航栏对学习内容的分类，可以根据自己的喜好选择要学习的类别。展示页面有标签，时间，是否学习。

点击后会进去相应的文章进行学习，学习后会有已学习的提示字样。

| <img src="images\Screenshot_20211130_163328_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163328_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163627_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163627_uni.UNID6348DE" style="zoom: 25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

### 图书界面功能介绍

图书界面有一些关于四史的数据，供大家参考。

内容上有图书封面，图书标题，图书的简介。点击相应的图书可以进入相应的图书内容介绍，有内容简介和目录等内容。

| <img src="images\Screenshot_20211130_163401_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163401_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163405_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163405_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163410_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163410_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |

### 我的页面功能介绍

#### 我的页面展示

未登录的情况下，可以点击登录跳转到登录页面进行登录。图二为登陆后的页面效果。

| <img src="images\Screenshot_20211130_163417_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163417_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163533_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163533_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

#### 编辑资料

点击编辑资料会跳转到资料编辑界面

点击头像可以查看自己头像大图。点击右下角的相机会跳转到相册，你可以选择一张喜欢的头像来跟换头像，头像会时时刷新到页面。编辑信息后点击保存，既可保存自己的资料信息，随后跳转到我的页面。

| <img src="images\Screenshot_20211130_173739_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173739_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_173750_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173750_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_173805_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173805_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="images\Screenshot_20211130_173816_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173816_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_174136_uni.UNID6348DE.jpg" alt="Screenshot_20211130_174136_uni.UNID6348DE" style="zoom:25%;" /> |                                                              |

#### 评论管理

如果没有评论，评论会显示一个没有评论，让你先去评论的提示。如有已有评论，会显示你评论的内容，以及你评论的资讯图片，点击会弹出查看资讯详情和删除评论操作。点击查看详情，会跳转到相应的详情页面。点击删除会删除自己的评论，以及在你评论下对你进行回复的评论。

| <img src="images\Screenshot_20211130_173601_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173601_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_173510_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173510_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_173513_uni.UNID6348DE.jpg" alt="Screenshot_20211130_173513_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |

#### APP其他功能

点击我要反馈会跳转到反馈页面，你可以对不满意的内容进行，反馈，提交给我们。也可以点击联系我们，查看我们的邮箱，给我们发私信。

| <img src="images\Screenshot_20211130_163428_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163428_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163432_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163432_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163436_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163436_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |

点击隐私政策，就可以查看隐私信息。以便你了解本应用的隐私政策。如果你是在登录状态点击修改密码，账号拦会自动填充你的账号，方便你的修改。

| <img src="images\Screenshot_20211130_181924_uni.UNID6348DE.jpg" alt="Screenshot_20211130_181924_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_174855_uni.UNID6348DE.jpg" alt="Screenshot_20211130_174855_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

点击版本说明会有我们版本相应的版本信息。点击关于我们会有关于我们的相应介绍。点击退出登录，会退出自己的账号，并给出提示。刷新我的个人主页数据页面。

| <img src="images\Screenshot_20211130_182100_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182100_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163454_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163454_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_163537_uni.UNID6348DE.jpg" alt="Screenshot_20211130_163537_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |

### 登录注册页面

#### 登录功能

登录页面如果没有输入邮箱，或者输入的邮箱格式不正确的话会提示你相应的信息。

密码不能小于六位。

如果该邮箱未注册的话，会提示这个邮箱未注册。

登录成功后会跳转到我的页面，显示你的相应的信息内容。

| <img src="images\Screenshot_20211130_182503_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182503_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_182528_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182528_uni.UNID6348DE" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="images\Screenshot_20211130_182540_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182540_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_182538_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182538_uni.UNID6348DE" style="zoom:25%;" /> |

#### 注册功能

点击注册账号跳转到注册页面，其他验证信息与登录的验证相同，点击获取验证码即可搜到验证码已发送的提示，之后你的邮箱里就会搜到一封验证邮件，输入验证码即可验证注册。

如果你为点击同意协议，会提示你同意协议，点击协议也查看相应的协议。如果不同意本协议，是无法进行找好的注册的。

如果验证码输入错误，或者超时有效时间为十分钟，就会提示验证码错误或者失效的提示。

| <img src="images\Screenshot_20211130_182828_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182828_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_182846_com.tencent.mobileqq.jpg" alt="Screenshot_20211130_182846_com.tencent.mobileqq" style="zoom:25%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="images\Screenshot_20211130_182909_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182909_uni.UNID6348DE" style="zoom:25%;" /> | <img src="images\Screenshot_20211130_182913_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182913_uni.UNID6348DE" style="zoom:25%;" /> |

#### 修改密码功能

功能与登录和注册功能类似，可以参考登录和注册功能。

<img src="images\Screenshot_20211130_182955_uni.UNID6348DE.jpg" alt="Screenshot_20211130_182955_uni.UNID6348DE" style="zoom:25%;" />

