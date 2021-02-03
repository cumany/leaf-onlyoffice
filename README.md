# Leaf-only office
## 目标
  **拿来即用**
  
运行项目指定 端口 默认端口8080

`java -jar leaf-onlyOffice.jar --server.port=9000`

添加参数--only-office.doc-service-api-url 修改为:only office提供的服务地址

`java -jar leaf-onlyOffice.jar --only-office.doc-service=http://192.168.25.138:9001`

> 项目运行会向`/logs/onlyOffice`文件夹中写入对应的日志 默认启动级别为dev

### 接口
访问地址（响应视图）
* `http://192.168.144.1:8080/onlyOffice/edit?url=D%3a%5cdemo.xlsx`
url 必须urlEncode编码 防止中文乱码
如果是本地文件必须使用ip+端口形式访问。

原因:文件会保存到本地可访问的地址，然后提供可访问的url给only office,only office根据url下载文件

* `/onlyOffice/editConfig` 返回only office需要的配置信息


* `/onlyOffice/save`   only office回调地址
当我们关闭编辑窗口后，十秒钟左右only office会将它存储的我们的编辑后的文件 调用该接口
通过提供的url下载文件 然后进行保存、移除、上传等操作 
 
### 流程  
1、传入文件路径url
* 本地文件（可以保存 替换源文件）
* 远程可访问文件
>（不可以保存，需要定义如何保存文件，如上传到指定位置。上传访问不固定所以未写）
> RemoteTempFileHandler 自定义实现

2、获取文件临时访问地址
默认保存在可访问的reports文件下

3、onlyOffice 页面展示（默认参数）

4、查看、编辑、下载、打印等等


> 更多功能请前往[OnlyOffice](https://api.onlyoffice.com)查看

