<template>
  <div>
    <h3>著者基本情報</h3>
    <p>
      <label>
        氏名
        <input type="text" placeholder="著者氏名を入力" v-model="name">
      </label>
    </p>
    <p>
      <button v-on:click="putAuthor">更新</button>
    </p>
    <p>
      <label>
        <button style="background-color: #f37b00" v-on:click="deleteAuthor">著者を削除</button>
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
    putAuthor() {
      this.$axios.put(this.detailUrl, {id: this.$route.params.authorId, name: this.name})
        .then(() => {
          alert('著者情報を更新しました。')
        }).catch(err => {
          alert('変更に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    },
    deleteAuthor() {
      let select = confirm('この著者を削除しますか？')
      if (select) {
        this.$axios.delete(this.detailUrl)
          .then(() => {
            alert('削除しました。')
            this.$router.push({ name: 'AuthorsView' })
          }).catch(err => {
            alert('削除に失敗しました。:' + err.response.status + err.response.statusText)
          })
      }
    }
  }
}
</script>

<style scoped>

</style>