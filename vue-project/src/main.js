import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import axios from "axios";
const app = createApp(App)
import store from "./store";

app.config.globalProperties.$axios = axios

router.beforeEach((to, from, next) => {
    // 假设我们用 isLoggedIn 作为一个全局状态来判断用户是否已登录
    const isLoggedIn = store.state.isLoggedIn; // 你可以从 Vuex 或者其他地方获取实际的登录状态
    const isLoginPage = to.path === '/'
    // 如果目标路由需要认证且用户未登录，跳转到登录页
    if (!isLoggedIn && !isLoginPage) {
        next('/'); // 强制跳转到登录页面
    }else{
        next();
    }
});
app.use(router).use(ElementPlus).use(store)

app.mount('#app')
