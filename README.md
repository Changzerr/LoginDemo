# SpringBoot+jwt+vue的一个登录验证

实现流程主要是登录后，后端创建一个 jwt 的 token 然后返回给前端，前端将数据存到 localStorage 里，然后在 router 里设置路由读取 localStorage 里的 token 数据，使每次访问路由都去后端验证一下 token  

![image-20211112172643959](https://gitee.com/stormwater/img-cloud/raw/master/image-20211112172643959.png)

### SpringBoot后端

#### 依赖

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    
    	<!--jwt的依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
    </dependencies>
```

#### JwtUtil类

JwtUtil 类主要功能就是创建 token 和 验证 token

```java
package com.zzz.login.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
public class JwtUtls {
    //token 过期时间
    private static long time = 1000*60*60*24;
    //token 的密钥
    private static String SING = "adADafnjk21^TJSVFD%$JFGE&^%DGHJDF^&min";

    //创建token方法
    public static String createToken(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header 
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username","admin")
                .claim("role","admin")
                .setExpiration(new Date(System.currentTimeMillis()+time))  // 过期时间
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,SING) //加密算法和签名密钥
                .compact();

        return jwtToken;
    }

    //验证token方法 如果为空或者解析失败都返回false
    public static boolean checkToken(String token){
        if(token==null){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SING).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

```

#### UserController类

UserController类用来定义接口和调用JwtUtil来创建token和解析token

```java
package com.zzz.login.controller;

import com.zzz.login.entity.User;
import com.zzz.login.utils.JwtUtls;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
@RestController
public class UserController {
    private final String USERNAME = "admin";
    private final String PASSWORD = "123456";

    //登录接口
    @PostMapping("/login")
    //接收post参数需要在方法的参数前面加@RequestBody才能获取到
    public User login(@RequestBody User user) {
        
        //验证用户名和密码这里用的写死的数据，之后会链接数据库来查真实的数据
        if(USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){
            //调用JwtUtil类方法添加token
            user.setToken(JwtUtls.createToken());
            return user;
        }
        return null;
    }

    
    //其他访问页面每次都要调用此接口来验证token
    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
        //由于token保存在header里，所以参数应该是HttpServletRequest
        //HttpServletRequest可以获取header里面的数据
        String token = request.getHeader("token");
        //调用JwtUtil类里面的检测方法
        return JwtUtls.checkToken(token);
    }


}
```

#### User实体类

用了Lombok简化的代码

```java
package com.zzz.login.entity;

import lombok.Data;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
@Data
public class User {
    private String username;
    private String password;
    private String token;

}
```

#### CrosConfiguration类

CrosConfiguration类解决跨域问题

```java
package com.zzz.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
@Configuration
public class crosConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","HEAD","POST","DELETE","PUT","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");

    }
}
```

### Vue前端

#### 登录页面

```vue
<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎 登录</h3>
      <el-form-item label=" 账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label=" 密码" prop="password">
        <el-input type="password" placeholder=" 请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit( 'loginForm' )">登录</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="dialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>​

<script>
  import {AxiosBasicCredentials as admin} from "axios";

  export default {
    name: "Login",
    data() {
      return {
        form: {
          username: '',
          password: ''
        },
        //表单验证，需要在el-form-item 元素中增加prop 属性
        rules: {
          username: [
            {required: true, message: " 账号不可为空", trigger: 'blur'}
          ],
          password: [
            {required: true, message: " 密码不可为空 ", trigger: 'blur'}
          ]
        },
		//对话框显示和隐藏
        dialogVisible: false
      }
    },
methods: {
  onSubmit(formName){
	//为表单绑定验证功能
    this.$refs [formName].validate((valid) => {
      if (valid) {
	//使用vue-router路由到指定页面，该方式称之为编程式导航
	//this.$router.push("/index");
        this.$ajax.post("http://localhost:8080/login",this.form).then(res=>{
          console.log(res.data);
          if(res.data!=null){
            localStorage.setItem("access-admin",JSON.stringify(res.data))
            this.$router.replace({path: '/index'});
          }
        })

      } else {
        this.dialogVisible = true;
        return false;
      }
    });
  }
}
  }
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }
</style>
```

#### 路由配置

配置了router.beforeEach( ) 之后每次路由跳转都会执行访问后端，来检测token是否合格

```js
const router = new Router({
  routes: []
})

router.beforeEach((to,from,next) => {
  if(to.path.startsWith('/login')) {
      //如果是login页面就继续执行
    window.localStorage.removeItem('access-admin')
    next()
  }else {
      
      //从localStorage里面拿之前存的数据
    let admin = JSON.parse(window.localStorage.getItem('access-admin'))
    //如果admin不存在就去login页面
    if(!admin) {
      next({path: '/login'})
    } else {
      axios({
        url: 'http://localhost:8080/checkToken',
        method: 'get',
          //在header里面放入token
        headers:{
          token: admin.token
        }
      }).then(response => {
        if(!response.data){
        console.log("校验失败")
          next({path: '/error'})
        }
      })
      next()
    }
  }
})

export default router
```

