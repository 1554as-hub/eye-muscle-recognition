import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from "../views/login/LoginPage";
import Content from "../views/home/Content";
import FileUploadView from "../views/fileupload/FileUploadView";
import SingleShowView from "../views/SingleShow/SingleShowView";
import HistoryResult from "../views/resultshow/HistoryResult";


Vue.use(VueRouter)

const routes = [
  {
    path: '/' ,
    redirect: '/login'
  },
  {
    path: '/login' ,
    component: LoginPage
  },
  {
    path: '/home' ,
    component: Content ,
    redirect: '/fileupload',
    children:
    [
      {
        path: '/fileupload',
        component: FileUploadView
      },
      {
        path: '/singleshow',
        component: SingleShowView
      },
      {
        path: '/historyshow',
        component: HistoryResult
      }
    ]
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to , from , next) => {
  if(to.path === '/login') return next();
  const  tokenStr = window.sessionStorage.getItem('token');
  if(!tokenStr) {
    return  next('/login');
  }
  next();
});


export default router
