<template>
  <div>
    <h3>著者基本情報</h3>
    <p>
      <label>
        氏名
        <input type="text" placeholder="著者氏名を入力" v-model="name">
        <button v-on:click="putName">更新</button>
      </label>
    </p>
    <h3>著書リスト</h3>
    <table>
      <thead>
      <tr>
        <th>タイトル</th>
        <th>出版日</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="book in books" :key="book.id">
        <td>
          <router-link :to="{ name: 'BooksDetailView', params: { bookId: book.id } }">
            {{ !book.title ? '（タイトル未定）' : book.title }}
          </router-link>
        </td>
        <td>{{ book.dateOfPublication }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'AuthorsDetailView',
  data() {
    return {
      name: null,
      books: []
    }
  },
  computed: {
    detailUrl() {
      return '/authors/' + this.$route.params.authorId
    }
  },
  watch: {
    $route() {
      this.getAuthor()
      this.getBooksByAuthor()
    }
  },
  mounted() {
    this.getAuthor()
    this.getBooksByAuthor()
  },
  methods: {
    getAuthor() {
      this.$axios.get(this.detailUrl)
        .then(response => {
          this.name = response.data.name
        }).catch(err => {
          alert('データがありません。:' + err.response.status + err.response.statusText)
        })
    },
    getBooksByAuthor() {
      this.$axios.get(this.detailUrl + '/books')
        .then(response => {
          this.books = response.data
        }).catch(err => {
          alert('データがありません。:' + err.response.status + err.response.statusText)
        })
    },
    putName() {
      this.$axios.put(this.detailUrl + '/name', this.name, { headers: { 'Content-Type': 'text/plain' } })
        .then(() => {
          alert('氏名を変更しました。')
        }).catch(err => {
          alert('変更に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    }
  }
}
</script>

<style scoped>

</style>