import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import App from './App.vue'
import router from './router'

require('@/assets/_import.scss')

Vue.use(VueAxios, axios)
Vue.config.productionTip = false
console.log(process.env.VUE_APP_API_BASE)
Vue.prototype.$axios = axios.create({ baseURL: process.env.VUE_APP_API_BASE })

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
