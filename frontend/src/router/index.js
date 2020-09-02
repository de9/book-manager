import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '@/views/HomeView'
import BooksView from '@/views/BooksView'
import AuthorsView from '@/views/AuthorsView'
import BooksRegisterView from '@/views/BooksRegisterView'
import AuthorsRegisterView from '@/views/AuthorsRegisterView'
import BooksDetailView from '@/views/BooksDetailView'
import AuthorsDetailView from '@/views/AuthorsDetailView'

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
  },
  {
    path: '/books/detail/:bookId',
    name: 'BooksDetailView',
    component: BooksDetailView
  },
  {
    path: '/authors/detail/:authorId',
    name: 'AuthorsDetailView',
    component: AuthorsDetailView
  }
]

const router = new VueRouter({
  routes
})

export default router
