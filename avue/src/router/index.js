import Vue from 'vue'
import Router from 'vue-router'
import Nav from "../components/Nav";
import Login from "../components/Login";
import Index from "../view/Index";
import Header from "../components/Header";
import Profile from "../components/Profile";
import BlogList from "../components/BlogList";
import TagList from "../components/TagList";
import UpdateUser from "../components/UpdateUser";
import AddUser from "../components/AddUser";
import BlogEdit from "../components/BlogEdit";
import UpdateBlog from "../components/UpdateBlog";
import CommentList from "../components/CommentList";
import CategoryList from "../components/CategoryList";
import Main from "../components/Main";
import NotFound from "../view/NotFound";
import axios from "axios";

Vue.use(Router)


const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/index'
    },
    {
      path: '/nav',
      component: Nav

    },{
      path: '/login',
      component: Login
    },
    {
      path: '/notFound',
      component: NotFound
    },{
      path: '/index',
      component: Index,
      name: 'index',
      meta: {title: '首页'},
      children: [
        {
          path: '/user/profile',
          name: 'user',
          meta: {title: '用户'},
          component: Profile
        },{
          path: '/user/updateUser',
          name: 'updateUser',
          meta: {title: '更新用户'},
          component: UpdateUser
        },{
          path: '/user/addUser',
          name: 'addUser',
          meta: {title: '添加用户'},
          component: AddUser
        },{
          path: '/content/blogList',
          name: 'blog',
          meta: {title: '博客列表'},
          component: BlogList
        },{
          path: '/tag',
          name: 'tag',
          meta: {title: '标签列表'},
          component: TagList
        },{
          path: '/content/blogEdit',
          name: 'blogEdit',
          meta: {title: '新建博客'},
          component: BlogEdit
        },{
          path: '/content/updateBlog',
          name: 'updateBlog',
          meta: {title: '更新博客'},
          component: UpdateBlog
        },{
          path: '/comment/commentList',
          name: 'commentList',
          meta: {title: '评论列表'},
          component: CommentList
        },{
          path: '/category',
          name: 'categoryList',
          meta: {title: '类别管理'},
          component: CategoryList
        },{
          path: '/main',
          name: 'main',
          meta: {title: '主页'},
          component: Main
        }
      ]
    },{
      path: '/header',
      component: Header
    },
    {
      path: '*',
      redirect: "notFound"
    }
  ]
})


router.beforeEach((to,from,next) => {
  if(to.path.startsWith('/login')) {
    window.localStorage.removeItem('access-admin')
    next()
  }else {
    let admin = JSON.parse(window.localStorage.getItem('access-admin'))
    if(!admin) {
      next({path: '/login'})
    } else {
      axios({
        url: 'http://localhost:8080/checkToken',
        method: 'get',
        headers:{
          token: admin.token
        }
      }).then(response => {
        console.log(response.data)
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
