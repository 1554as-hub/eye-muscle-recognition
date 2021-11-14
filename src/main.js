import Vue from 'vue'
import App from './App.vue'
import ElementUI ,{Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import axios from 'axios'

Vue.use(ElementUI);
axios.defaults.baseURL = 'http://localhost:7171'
Vue.prototype.$http = axios
Vue.use(ElementUI);
Vue.prototype.$message = Message

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
