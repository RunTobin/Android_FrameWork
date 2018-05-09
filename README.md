一.Retrofit使用步骤：

1、导包

    compile ‘com.squareup.retrofit2:retrofit:2.0.0-beta4’ 
    compile ‘com.squareup.retrofit2:converter-gson:2.0.0-beta4’

2、了解Retrofit2中的网络访问常用注解接口,其实这些接口都是在retrofit2.http这个包下面的

    1、@GET GET网络请求方式 
    2、@POST POST网络请求方式 
    3、@Headers() 头信息参数 
    4、@Path() 路径参数，替换url地址中 { } 所括的部分 
    5、@Query() 查询参数，将在url地址中追加类似“page=1”的字符串，形成提交给服务端的请求参数 
    6、@QueryMap 查询参数集合，将在url地址中追加类似 
    “type=text&username=abc&password=123”的字符串 
    7、@FormUrlEncoded 对表单域中填写的内容进行编码处理，避免乱码 
    8、@Field() 指定form表单域中每个空间的额name以及相应的数值 
    9、@FieldMap 表单域集合 
    10、@Multipart Post提交分块请求，如果上传文件，必须指定Multipart 
    11、@Body Post提交分块请求

3、代码步骤：

    1、定义一个接口（封装URL地址和数据请求） 
    2、实例化Retrofit 
    3、通过Retrofit实例创建接口服务对象 
    4、接口服务对象调用接口中方法，获得Call对象 
    5、Call对象执行请求（异步、同步请求）


1、定义一个接口（封装URL地址和数据请求） RequestServices.java

    package com.example.eventbus.retrofittest;

    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.http.GET;
    public interface RequestServices {
        //请求方式为GET，参数为basil2style，因为没有变量所以下面getString方法也不需要参数
        @GET("basil2style") 
        //定义返回的方法，返回的响应体使用了ResponseBody
        Call<ResponseBody> getString();
    }


我们通常把基础地址都放在一个类里，方便调用 Constant.java

    package com.example.eventbus.retrofittest;
    public class Constant {
        //baseurl
        public final static String URL_BASE = "https://api.github.com/users/";
    }

2、实例化Retrofit,获取Retrofit对象，设置地址

    Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.URL_BASE)
                        .build();            
      
3、通过Retrofit实例创建接口服务对象

    RequestServices requestServices = retrofit.create(RequestServices.class);

4、接口服务对象调用接口中方法，获得Call对象

    Call<ResponseBody> call = requestServices.getString();

5、Call对象执行请求（异步、同步请求）

    call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.isSuccess()){
                   try {
                       Log.i("LHD",response.body().toString());
                       //返回的结果保存在response.body()中
                       String result = response.body().string();
                       //onResponse方法是运行在主线程也就是UI线程的，所以我们可以在这里
                       //直接更新UI
                       textView.setText(result);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("LHD","访问失败");
            }
        });
        
二.OkHttp使用步骤

