# OpenAPI definition


**简介**:OpenAPI definition


**HOST**:http://localhost:8123/api


**联系人**:


**Version**:v0


**接口路径**:/api/v3/api-docs/default


[TOC]






# user-controller


## updateUser


**接口地址**:`/api/user/update`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "userName": "",
  "userAvatar": "",
  "userProfile": "",
  "userRole": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userUpdateRequest|UserUpdateRequest|body|true|UserUpdateRequest|UserUpdateRequest|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;userName|||false|string||
|&emsp;&emsp;userAvatar|||false|string||
|&emsp;&emsp;userProfile|||false|string||
|&emsp;&emsp;userRole|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## register


**接口地址**:`/api/user/register`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "userAccount": "",
  "userPassword": "",
  "checkPassword": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userRegisterRequest|UserRegisterRequest|body|true|UserRegisterRequest|UserRegisterRequest|
|&emsp;&emsp;userAccount|||false|string||
|&emsp;&emsp;userPassword|||false|string||
|&emsp;&emsp;checkPassword|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseLong|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||integer(int64)|integer(int64)|
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": 0,
	"message": ""
}
```


## userLogout


**接口地址**:`/api/user/logout`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## userLogin


**接口地址**:`/api/user/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "userAccount": "",
  "userPassword": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userLoginRequest|UserLoginRequest|body|true|UserLoginRequest|UserLoginRequest|
|&emsp;&emsp;userAccount|||false|string||
|&emsp;&emsp;userPassword|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseLoginUserVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||LoginUserVO|LoginUserVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userAccount||string||
|&emsp;&emsp;userName||string||
|&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;userProfile||string||
|&emsp;&emsp;userRole||string||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"id": 0,
		"userAccount": "",
		"userName": "",
		"userAvatar": "",
		"userProfile": "",
		"userRole": "",
		"createTime": "",
		"updateTime": ""
	},
	"message": ""
}
```


## listUserVOByPage


**接口地址**:`/api/user/list/page/vo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "pageNum": 0,
  "pageSize": 0,
  "sortField": "",
  "sortOrder": "",
  "id": 0,
  "userName": "",
  "userAccount": "",
  "userProfile": "",
  "userRole": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userQueryRequest|UserQueryRequest|body|true|UserQueryRequest|UserQueryRequest|
|&emsp;&emsp;pageNum|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;sortField|||false|string||
|&emsp;&emsp;sortOrder|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;userName|||false|string||
|&emsp;&emsp;userAccount|||false|string||
|&emsp;&emsp;userProfile|||false|string||
|&emsp;&emsp;userRole|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponsePageUserVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||PageUserVO|PageUserVO|
|&emsp;&emsp;records||array|UserVO|
|&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;userAccount||string||
|&emsp;&emsp;&emsp;&emsp;userName||string||
|&emsp;&emsp;&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;&emsp;&emsp;userProfile||string||
|&emsp;&emsp;&emsp;&emsp;userRole||string||
|&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;pageNumber||integer(int64)||
|&emsp;&emsp;pageSize||integer(int64)||
|&emsp;&emsp;totalPage||integer(int64)||
|&emsp;&emsp;totalRow||integer(int64)||
|&emsp;&emsp;optimizeCountQuery||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"userAccount": "",
				"userName": "",
				"userAvatar": "",
				"userProfile": "",
				"userRole": "",
				"createTime": ""
			}
		],
		"pageNumber": 0,
		"pageSize": 0,
		"totalPage": 0,
		"totalRow": 0,
		"optimizeCountQuery": true
	},
	"message": ""
}
```


## getLoginUser


**接口地址**:`/api/user/get/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseLoginUserVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||LoginUserVO|LoginUserVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userAccount||string||
|&emsp;&emsp;userName||string||
|&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;userProfile||string||
|&emsp;&emsp;userRole||string||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"id": 0,
		"userAccount": "",
		"userName": "",
		"userAvatar": "",
		"userProfile": "",
		"userRole": "",
		"createTime": "",
		"updateTime": ""
	},
	"message": ""
}
```


## deleteUser


**接口地址**:`/api/user/delete`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|deleteRequest|DeleteRequest|body|true|DeleteRequest|DeleteRequest|
|&emsp;&emsp;id|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## addUser


**接口地址**:`/api/user/add`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "userName": "",
  "userAccount": "",
  "userAvatar": "",
  "userProfile": "",
  "userRole": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userAddRequest|UserAddRequest|body|true|UserAddRequest|UserAddRequest|
|&emsp;&emsp;userName|||false|string||
|&emsp;&emsp;userAccount|||false|string||
|&emsp;&emsp;userAvatar|||false|string||
|&emsp;&emsp;userProfile|||false|string||
|&emsp;&emsp;userRole|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseLong|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||integer(int64)|integer(int64)|
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": 0,
	"message": ""
}
```


## getUserById


**接口地址**:`/api/user/get`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseUser|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||User|User|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userAccount||string||
|&emsp;&emsp;userPassword||string||
|&emsp;&emsp;userName||string||
|&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;userProfile||string||
|&emsp;&emsp;userRole||string||
|&emsp;&emsp;editTime||string(date-time)||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;isDelete||integer(int32)||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"id": 0,
		"userAccount": "",
		"userPassword": "",
		"userName": "",
		"userAvatar": "",
		"userProfile": "",
		"userRole": "",
		"editTime": "",
		"createTime": "",
		"updateTime": "",
		"isDelete": 0
	},
	"message": ""
}
```


## getUserVOById


**接口地址**:`/api/user/get/vo`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseUserVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||UserVO|UserVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userAccount||string||
|&emsp;&emsp;userName||string||
|&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;userProfile||string||
|&emsp;&emsp;userRole||string||
|&emsp;&emsp;createTime||string(date-time)||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"id": 0,
		"userAccount": "",
		"userName": "",
		"userAvatar": "",
		"userProfile": "",
		"userRole": "",
		"createTime": ""
	},
	"message": ""
}
```


# app-controller


## updateApp


**接口地址**:`/api/app/update`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "appName": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appUpdateRequest|AppUpdateRequest|body|true|AppUpdateRequest|AppUpdateRequest|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;appName|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## listMyAppVOByPage


**接口地址**:`/api/app/my/list/page/vo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "pageNum": 0,
  "pageSize": 0,
  "sortField": "",
  "sortOrder": "",
  "id": 0,
  "appName": "",
  "cover": "",
  "initPrompt": "",
  "codeGenType": "",
  "deployKey": "",
  "priority": 0,
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appQueryRequest|AppQueryRequest|body|true|AppQueryRequest|AppQueryRequest|
|&emsp;&emsp;pageNum|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;sortField|||false|string||
|&emsp;&emsp;sortOrder|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;appName|||false|string||
|&emsp;&emsp;cover|||false|string||
|&emsp;&emsp;initPrompt|||false|string||
|&emsp;&emsp;codeGenType|||false|string||
|&emsp;&emsp;deployKey|||false|string||
|&emsp;&emsp;priority|||false|integer(int32)||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponsePageAppVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||PageAppVO|PageAppVO|
|&emsp;&emsp;records||array|AppVO|
|&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;appName||string||
|&emsp;&emsp;&emsp;&emsp;cover||string||
|&emsp;&emsp;&emsp;&emsp;initPrompt||string||
|&emsp;&emsp;&emsp;&emsp;codeGenType||string||
|&emsp;&emsp;&emsp;&emsp;deployKey||string||
|&emsp;&emsp;&emsp;&emsp;deployedTime||string||
|&emsp;&emsp;&emsp;&emsp;priority||integer||
|&emsp;&emsp;&emsp;&emsp;userId||integer||
|&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;&emsp;&emsp;updateTime||string||
|&emsp;&emsp;&emsp;&emsp;user||UserVO|UserVO|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAccount||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userProfile||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userRole||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;pageNumber||integer(int64)||
|&emsp;&emsp;pageSize||integer(int64)||
|&emsp;&emsp;totalPage||integer(int64)||
|&emsp;&emsp;totalRow||integer(int64)||
|&emsp;&emsp;optimizeCountQuery||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"appName": "",
				"cover": "",
				"initPrompt": "",
				"codeGenType": "",
				"deployKey": "",
				"deployedTime": "",
				"priority": 0,
				"userId": 0,
				"createTime": "",
				"updateTime": "",
				"user": {
					"id": 0,
					"userAccount": "",
					"userName": "",
					"userAvatar": "",
					"userProfile": "",
					"userRole": "",
					"createTime": ""
				}
			}
		],
		"pageNumber": 0,
		"pageSize": 0,
		"totalPage": 0,
		"totalRow": 0,
		"optimizeCountQuery": true
	},
	"message": ""
}
```


## listGoodAppVOByPage


**接口地址**:`/api/app/good/list/page/vo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "pageNum": 0,
  "pageSize": 0,
  "sortField": "",
  "sortOrder": "",
  "id": 0,
  "appName": "",
  "cover": "",
  "initPrompt": "",
  "codeGenType": "",
  "deployKey": "",
  "priority": 0,
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appQueryRequest|AppQueryRequest|body|true|AppQueryRequest|AppQueryRequest|
|&emsp;&emsp;pageNum|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;sortField|||false|string||
|&emsp;&emsp;sortOrder|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;appName|||false|string||
|&emsp;&emsp;cover|||false|string||
|&emsp;&emsp;initPrompt|||false|string||
|&emsp;&emsp;codeGenType|||false|string||
|&emsp;&emsp;deployKey|||false|string||
|&emsp;&emsp;priority|||false|integer(int32)||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponsePageAppVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||PageAppVO|PageAppVO|
|&emsp;&emsp;records||array|AppVO|
|&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;appName||string||
|&emsp;&emsp;&emsp;&emsp;cover||string||
|&emsp;&emsp;&emsp;&emsp;initPrompt||string||
|&emsp;&emsp;&emsp;&emsp;codeGenType||string||
|&emsp;&emsp;&emsp;&emsp;deployKey||string||
|&emsp;&emsp;&emsp;&emsp;deployedTime||string||
|&emsp;&emsp;&emsp;&emsp;priority||integer||
|&emsp;&emsp;&emsp;&emsp;userId||integer||
|&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;&emsp;&emsp;updateTime||string||
|&emsp;&emsp;&emsp;&emsp;user||UserVO|UserVO|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAccount||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userProfile||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userRole||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;pageNumber||integer(int64)||
|&emsp;&emsp;pageSize||integer(int64)||
|&emsp;&emsp;totalPage||integer(int64)||
|&emsp;&emsp;totalRow||integer(int64)||
|&emsp;&emsp;optimizeCountQuery||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"appName": "",
				"cover": "",
				"initPrompt": "",
				"codeGenType": "",
				"deployKey": "",
				"deployedTime": "",
				"priority": 0,
				"userId": 0,
				"createTime": "",
				"updateTime": "",
				"user": {
					"id": 0,
					"userAccount": "",
					"userName": "",
					"userAvatar": "",
					"userProfile": "",
					"userRole": "",
					"createTime": ""
				}
			}
		],
		"pageNumber": 0,
		"pageSize": 0,
		"totalPage": 0,
		"totalRow": 0,
		"optimizeCountQuery": true
	},
	"message": ""
}
```


## deployApp


**接口地址**:`/api/app/deploy`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "appId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appDeployRequest|AppDeployRequest|body|true|AppDeployRequest|AppDeployRequest|
|&emsp;&emsp;appId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||string||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": "",
	"message": ""
}
```


## deleteApp


**接口地址**:`/api/app/delete`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|deleteRequest|DeleteRequest|body|true|DeleteRequest|DeleteRequest|
|&emsp;&emsp;id|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## updateAppByAdmin


**接口地址**:`/api/app/admin/update`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "appName": "",
  "cover": "",
  "priority": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appAdminUpdateRequest|AppAdminUpdateRequest|body|true|AppAdminUpdateRequest|AppAdminUpdateRequest|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;appName|||false|string||
|&emsp;&emsp;cover|||false|string||
|&emsp;&emsp;priority|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## listAppVOByPageByAdmin


**接口地址**:`/api/app/admin/list/page/vo`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "pageNum": 0,
  "pageSize": 0,
  "sortField": "",
  "sortOrder": "",
  "id": 0,
  "appName": "",
  "cover": "",
  "initPrompt": "",
  "codeGenType": "",
  "deployKey": "",
  "priority": 0,
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appQueryRequest|AppQueryRequest|body|true|AppQueryRequest|AppQueryRequest|
|&emsp;&emsp;pageNum|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;sortField|||false|string||
|&emsp;&emsp;sortOrder|||false|string||
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;appName|||false|string||
|&emsp;&emsp;cover|||false|string||
|&emsp;&emsp;initPrompt|||false|string||
|&emsp;&emsp;codeGenType|||false|string||
|&emsp;&emsp;deployKey|||false|string||
|&emsp;&emsp;priority|||false|integer(int32)||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponsePageAppVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||PageAppVO|PageAppVO|
|&emsp;&emsp;records||array|AppVO|
|&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;appName||string||
|&emsp;&emsp;&emsp;&emsp;cover||string||
|&emsp;&emsp;&emsp;&emsp;initPrompt||string||
|&emsp;&emsp;&emsp;&emsp;codeGenType||string||
|&emsp;&emsp;&emsp;&emsp;deployKey||string||
|&emsp;&emsp;&emsp;&emsp;deployedTime||string||
|&emsp;&emsp;&emsp;&emsp;priority||integer||
|&emsp;&emsp;&emsp;&emsp;userId||integer||
|&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;&emsp;&emsp;updateTime||string||
|&emsp;&emsp;&emsp;&emsp;user||UserVO|UserVO|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAccount||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userProfile||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;userRole||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;createTime||string||
|&emsp;&emsp;pageNumber||integer(int64)||
|&emsp;&emsp;pageSize||integer(int64)||
|&emsp;&emsp;totalPage||integer(int64)||
|&emsp;&emsp;totalRow||integer(int64)||
|&emsp;&emsp;optimizeCountQuery||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"records": [
			{
				"id": 0,
				"appName": "",
				"cover": "",
				"initPrompt": "",
				"codeGenType": "",
				"deployKey": "",
				"deployedTime": "",
				"priority": 0,
				"userId": 0,
				"createTime": "",
				"updateTime": "",
				"user": {
					"id": 0,
					"userAccount": "",
					"userName": "",
					"userAvatar": "",
					"userProfile": "",
					"userRole": "",
					"createTime": ""
				}
			}
		],
		"pageNumber": 0,
		"pageSize": 0,
		"totalPage": 0,
		"totalRow": 0,
		"optimizeCountQuery": true
	},
	"message": ""
}
```


## deleteAppByAdmin


**接口地址**:`/api/app/admin/delete`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|deleteRequest|DeleteRequest|body|true|DeleteRequest|DeleteRequest|
|&emsp;&emsp;id|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseBoolean|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||boolean||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": true,
	"message": ""
}
```


## addApp


**接口地址**:`/api/app/add`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "initPrompt": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appAddRequest|AppAddRequest|body|true|AppAddRequest|AppAddRequest|
|&emsp;&emsp;initPrompt|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseLong|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||integer(int64)|integer(int64)|
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": 0,
	"message": ""
}
```


## getAppVOById


**接口地址**:`/api/app/get/vo`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseAppVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||AppVO|AppVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;appName||string||
|&emsp;&emsp;cover||string||
|&emsp;&emsp;initPrompt||string||
|&emsp;&emsp;codeGenType||string||
|&emsp;&emsp;deployKey||string||
|&emsp;&emsp;deployedTime||string(date-time)||
|&emsp;&emsp;priority||integer(int32)||
|&emsp;&emsp;userId||integer(int64)||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;user||UserVO|UserVO|
|&emsp;&emsp;&emsp;&emsp;id||integer||
|&emsp;&emsp;&emsp;&emsp;userAccount||string||
|&emsp;&emsp;&emsp;&emsp;userName||string||
|&emsp;&emsp;&emsp;&emsp;userAvatar||string||
|&emsp;&emsp;&emsp;&emsp;userProfile||string||
|&emsp;&emsp;&emsp;&emsp;userRole||string||
|&emsp;&emsp;&emsp;&emsp;createTime||string||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {
		"id": 0,
		"appName": "",
		"cover": "",
		"initPrompt": "",
		"codeGenType": "",
		"deployKey": "",
		"deployedTime": "",
		"priority": 0,
		"userId": 0,
		"createTime": "",
		"updateTime": "",
		"user": {
			"id": 0,
			"userAccount": "",
			"userName": "",
			"userAvatar": "",
			"userProfile": "",
			"userRole": "",
			"createTime": ""
		}
	},
	"message": ""
}
```


## chatToGenCode


**接口地址**:`/api/app/chat/gen/code`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`text/event-stream`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appId||query|true|integer(int64)||
|message||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ServerSentEventString|


**响应参数**:


暂无


**响应示例**:
```javascript
[
	null
]
```


# static-resource-controller


## serveStaticResource


**接口地址**:`/api/static/{deployKey}/**`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|deployKey||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```


# health


## health


**接口地址**:`/api/health/`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|BaseResponseString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||string||
|message||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": "",
	"message": ""
}
```