import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from  './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {postKerValueRequest} from './utils/api';
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
//动态获取菜单按钮
import {initMenu} from "./utils/menus";
//引入font-awesome样式
import 'font-awesome/css/font-awesome.min.css'

Vue.prototype.postKerValueRequest=postKerValueRequest;
Vue.prototype.postRequest=postRequest;
Vue.prototype.putRequest=putRequest;
Vue.prototype.getRequest=getRequest;
Vue.prototype.deleteRequest=deleteRequest;


Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});

//全局导航守卫
router.beforeEach((to,from,next)=>{

    if (to.path=='/'){
        next();
    }else {
        if(window.sessionStorage.getItem("user")){
            initMenu(router,store);
            next();
        }else{
            next("/?redirect="+to.path);
        }

    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
