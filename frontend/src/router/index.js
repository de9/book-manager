import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView'
import BooksView from '@/views/BooksView'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: '/books',
    name: 'BooksView',
    component: BooksView
  }
]

const router = new VueRouter({
  routes
})

export default router
