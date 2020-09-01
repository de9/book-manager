import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView'
import BooksView from '@/views/BooksView'
import AuthorsView from '@/views/AuthorsView'
import BooksRegisterView from '@/views/BooksRegisterView'

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
  },
  {
    path: '/authors',
    name: 'AuthorsView',
    component: AuthorsView
  },
  {
    path: '/books/register',
    name: 'BooksRegisterView',
    component: BooksRegisterView
  }
]

const router = new VueRouter({
  routes
})

export default router
