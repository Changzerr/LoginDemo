<template>


    <el-container >
      <el-aside  width="280px">
        <el-menu>
          <router-link to="/main" class="sidebar">
            <el-menu-item index="0-0" >
              首页
            </el-menu-item>
          </router-link>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-caret-right"></i>博客管理</template>
            <el-menu-item-group>
              <router-link to="/content/blogEdit" class="sidebar">
                <el-menu-item index="2-2">
                  写博客
                </el-menu-item>
              </router-link>

              <router-link to="/content/blogList" class="sidebar">
                <el-menu-item index="2-1">
                  博客信息管理
                </el-menu-item>
              </router-link>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="1">
            <template slot="title"><i class="el-icon-caret-right"></i>评论管理</template>
            <el-menu-item-group>
              <router-link to="/comment/commentList" class="sidebar">
                <el-menu-item index="1-1">
                  评论管理
                </el-menu-item>
              </router-link>

            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-caret-right"></i>标签类别管理</template>
            <el-menu-item-group>

              <router-link to="/tag" class="sidebar">
                <el-menu-item index="5-1">
                  博客标签管理
                </el-menu-item>
              </router-link>
              <router-link to="/category" class="sidebar">
                <el-menu-item index="5-3">
                  博客类别管理
                </el-menu-item>
              </router-link>

            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-caret-right"></i>点点滴滴管理</template>
            <el-menu-item-group>
              <router-link to="/user/profile" class="sidebar">
                <el-menu-item index="4-1">
                  时光轴管理
                </el-menu-item></router-link>
              <router-link to="/tag" class="sidebar">
                <el-menu-item index="4-3">
                  照片墙管理
                </el-menu-item>
              </router-link>

            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="6">
            <template slot="title"><i class="el-icon-caret-right"></i>网站信息管理</template>
            <el-menu-item-group>
              <router-link to="/user/profile" class="sidebar">
                <el-menu-item index="6-1">
                  友情链接管理
                </el-menu-item>
              </router-link>
              <router-link to="/user/profile" class="sidebar">
                <el-menu-item index="6-2">
                  博客签名管理
                </el-menu-item>
              </router-link>
              <router-link to="/tag" class="sidebar">
                <el-menu-item index="6-3">
                  博客鸡汤管理
                </el-menu-item>
              </router-link>

            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="7">
            <template  slot="title"><i class="el-icon-caret-right"></i>系统管理</template>
            <el-menu-item-group>
              <router-link to="/user/profile" class="sidebar">
                <el-menu-item index="7-1">
                  修改账号密码
                </el-menu-item>
              </router-link>
              <router-link to="/user/profile" class="sidebar">
                <el-menu-item index="7-2">
                  刷新系统缓存
                </el-menu-item>
              </router-link>

            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>​

    </el-container>


</template>
<script>

  export default {
    name: "Nav",

  }
</script>

<style scoped>
.sidebar{
  text-decoration:none;
}
</style>​
