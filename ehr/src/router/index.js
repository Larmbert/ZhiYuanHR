import Vue from 'vue';
import VueRouter from 'vue-router';
import Login from '../views/Login.vue';
import Home from "../views/Home";
import WeChat from "../views/chat/WeChat";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
        hidden:true
    },
    {
        path: '/home',
        name: 'Home',
        component: Home,
        hidden:true,
        meta:{
            roles:['admin','user']
        },
        children: [
            {
                path: '/chat',
                name: '在线聊天',
                component: WeChat,
                hidden:true
            }
        ]
    },

    // ,
    // {
    //     path: '/home',
    //     name: '导航',
    //     component: Home,
    //     children:[
    //         {
    //             path:'/view1',
    //             name:'视图1',
    //             component:view1
    //         },
    //         {
    //             path: '/view2',
    //             name: '视图2',
    //             component: view2
    //         }
    //     ]
    // }

    // {
    //   path: '/about',
    //   name: 'About',
    //   // route level code-splitting
    //   // this generates a separate chunk (about.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    // }
]

const router = new VueRouter({
    routes
})

export default router
