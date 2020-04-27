#### 1、博物馆相关
##### （1）获取一个博物馆的基本信息
	请求方式：POST   
	必填参数：博物馆名称 
		{
		"mid":0,
		"name":"博物馆0",
		"star":该博物馆的平均分数
		"imgurl":
		"address":
		"introduction":“2333”,
		"opentime":“8:00am-5:00pm”,
		"exhibitions":[{eid:1,name:1,imgurl:图片1},{eid:2,name:2,imgurl:图片2},...]
		"collections":[{cid:1,name:1,imgurl:图片1},{cid:2,name:2,imgurl:图片2},...],
		"educations":[{cid:1,name:1,imgurl:图片1,time:2019-03-04},{cid:2,name:2,imgurl:图片2,time:2012-09-09},...]
		"news":[{nid:1,title:1,imgurl:图片1,author:"1",releasetime: },{nid:2,name:2,imgurl:图片2,author:"2",releasetime: },...]
		"comments":[{uid:1,avatar:图片1,time:2019-02-20,content:"评论1"},{uid:2,avatar:图片2,time:2019-02-20,content:"评论2"},...]
		}

  ##### （2）获取所有博物馆的基本信息（方便APP端以列表方式展示，按star从大到小排序）
	请求方式：GET
	返回数据：
		{
		{
		mid:1,
		name:1,
		imgurl:图片 1,
		star:博物馆分数1
		}，
		{
		mid:2,
		name:2,
		imgurl:图片2，
		star:博物馆分数2	
		},
		...
		}
	

#### 2、获取展览信息 
##### （1）获取单个展览信息（根据id）
	请求方式：POST
	必填参数：eid
	返回数据：{
	"eid":0,
	"name":"震惊！博物馆0的惊天藏品！",
	"imgurl":"",
	"mname":所属博物馆名字,                 
	"maddress":"北京三和胡同996号",   
	"time":"2020-04-29, 8:00am",？时间他好像获取不到
	"introduction":"这个展览由ACM大牛举办，为世界第一届ACM展览会。到场的嘉宾有AAA，BBB……",
	"explainations":{该展览的所有讲解}
	}
 ##### （2）获取单个展览信息（根据展览的name）
	请求方式：POST
 	必填参数：name
	返回数据同上
    
##### （3）获取所有展览的信息（APP端可以以列表的形式展现）
	请求方式：GET
	返回数据：{
		{
		"eid":0,
		"name":展览的名称,
		"mname":所属博物馆名称,
		"imgurl":展览的图片
		},
		{
		"eid":1,
		"name":展览的名称,
		"mname":所属博物馆名称,
		"imgurl":展览的图片
		},...
		}
	

#### 3、获取藏品信息
##### （1）获取单个藏品的信息         （根据cid）
	请求方式：POST
	必填参数：cid
	返回数据：{
		cid:
		name:
		imgurl:
		mname:所属博物馆名字.
		maddress:博物馆地址,
		introduce:
		explainations:{讲解格式待定}
		}
##### （2）获取单个藏品的信息         （根据name）
	请求方式：POST
	必填参数：name
	返回数据：{
		cid:
		name:
		imgurl:
		mname:所属博物馆名字，
		maddress:博物馆地址,
		introduce:
		explainations:{讲解格式待定}
		}
##### （3）获取所有藏品的信息（方便APP端以列表方式展示）
	请求方式：GET
	返回数据：{
		{
		cid:0,
		name:藏品名称,
		imgurl:图片1,
		mname:所属博物馆名称
		},
		{
		cid:1,
		name:藏品名称,
		imgurl:图片2,
		mname:所属博物馆名称
		},...
		}	

#### 4、获取教育活动信息
##### （1）获取单个教育活动的信息         （根据cid）
	请求方式：POST
	必填参数：oid
	返回数据：{
		oid:
		name:
		imgurl:
       time:
		mname:所属博物馆名字.
		maddress:博物馆地址,
		introduce:
		}

#### 5、获取新闻信息
##### （1）获取单个新闻的具体内容（根据nid）
	请求方式：POST
	必填参数：nid
	返回数据：{
		nid:1,
		title:"新闻标题1",
		author:"w1",
		releasetime:2019-04-01,
		extract:"摘要1",
		imgurl:"新闻图片",
		content:"新闻内容",
		url:"新闻的具体内容",
		}
	
  ##### （2）获取所有的新闻（按时间排序）
	请求方式：GET
	返回数据：{
		{
		nid:1,
		title:"新闻标题1",
		author:"w1",
		releasetime:2019-04-01,
		imgurl:"新闻图片url",
		nature:1
		},
		{
		nid:2,
		title:"新闻标题2",
		author:"w2",
		releasetime:2019-04-01,
		imgurl:"新闻图片url2",
		nature:0
		},...
		}    
                nature:表示新闻是否是正面新闻

#### 6、用户评论相关	
 ##### （1）上传打分（仅有博物馆打分）
	    请求方式：POST
        参数：
		{
		uid:
		mid:
		exhibitionstar:
		servicestar:
		environmentstar:
		}
	     返回  {state:1}(1为成功，0为失败)
	（3）上传评论（对博物馆）
	    POST
		参数：{
		  uid:
	      mid:
		  time:
		  content:
		}
	   返回{state:1,cid(标号)?}  (state值1为成功，0为失败）

#### 7、用户相关
##### （1）收藏展览
		请求方式：POST
		{
       uid:1,
		eid:1	
		}
##### （2）收藏藏品
		请求方式：POST
		{uid:1,
		cid:1	
		}
##### （3）收藏教育活动
		请求方式：POST
		{uid:1,
		oid:1	
		}
##### （4）收藏的新闻
		请求方式：POST
		{uid:1,
		nid:1	
		}
       以上都返回一个state参数：1为成功，0为失败 
##### （5）获取一个用户所有收藏的展览
		请求方式：POST
		参数：uid
		{
		     {
		"star_eid":0,
		"name":展览的名称,
		"mname":所属博物馆名称,
		"imgurl":展览的图片
		},
		{
		"star_eid":1,
		"name":展览的名称,
		"mname":所属博物馆名称,
		"imgurl":展览的图片
		},...
		}
		
##### (6)获取一个用户所有收藏的新闻
		POST
		参数：nid
		{
		{
		star_nid:1,
		title:"新闻标题1",
		author:"w1",
		releasetime:2019-04-01,
		imgurl:"新闻图片url",
		nature:1
		},
		{
		star_nid:2,
		title:"新闻标题2",
		author:"w2",
		releasetime:2019-04-01,
		imgurl:"新闻图片url2",
		nature:0
		},...
		}
##### (7)获取用户所有收藏的藏品
		POST请求参数uid
		{
		{
		star_cid:0,
		name:藏品名称,
		imgurl:图片1,
		mname:所属博物馆名称
		},
		{
		star_cid:1,
		name:藏品名称,
		imgurl:图片2,
		mname:所属博物馆名称
		},...
		}	
##### 	(8)获取用户所有收藏的教育活动
		POST请求参数uid
		返回数据：{
		{
		star_oid:0,
		name:藏品名称,
		imgurl:图片1,
		mname:所属博物馆名称
		},
		{
		star_oid:1,
		name:藏品名称,
		imgurl:图片2,
		mname:所属博物馆名称
		},...
		}	