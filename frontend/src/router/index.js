import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView'
import BooksView from '@/views/BooksView'
import AuthorsView from '@/views/AuthorsView'
import BooksRegisterView from '@/views/BooksRegisterView'
import AuthorsRegisterView from '@/views/AuthorsRegisterView'

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
  },
  {
    path: '/authors/register',
    name: 'AuthorsRegisterView',
    component: AuthorsRegisterView
  }
]

const router = new VueRouter({
  routes
})

export default router
