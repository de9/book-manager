<template>
  <div>
    <label>
      <input type="text" placeholder="書籍タイトルを入力" v-model="title">
      <button v-on:click="getBooks">検索</button>
    </label>
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
  name: 'BooksView',
  data() {
    return {
      title: null,
      books: []
    }
  },
  mounted() {
    this.getBooks()
  },
  methods: {
    async getBooks() {
      let response = await this.$axios.get('/books', {params: {title: this.title}})
      this.books = response.data
    }
  }
}
</script>

<style scoped>

</style>