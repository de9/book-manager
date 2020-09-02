<template>
  <div>
    <h3>書籍基本情報</h3>
    <p>
      <label>
        タイトル
        <input type="text" placeholder="書籍タイトルを入力" v-model="title">
        <button v-on:click="putTitle">更新</button>
      </label>
    </p>
    <p>
      <label>
        出版日
        <input type="date" placeholder="書籍出版日を入力" v-model="dateOfPublication">
        <button v-on:click="putDateOfPublication">更新</button>
      </label>
    </p>
    <p>
      <label>
        著者：
        <label v-for="author in authors" :key="author.id">
          <router-link :to="{ name: 'AuthorsDetailView', params: { authorId: author.id } }">
            {{ author.name }}
          </router-link>
          、
        </label>
      </label>
    </p>
    <p>
      <label>
        <button style="background-color: #f37b00" v-on:click="deleteBook">書籍を削除</button>
      </label>
    </p>
    <h3>著者を追加</h3>
    <label>
      <input type="text" placeholder="著者氏名を入力" v-model="searchName">
      <button v-on:click="getAllAuthors">検索</button>
    </label>
    <table>
      <thead>
      <tr>
        <th></th>
        <th>氏名</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="author in otherThanAuthors" :key="author.id">
        <td style="white-space: nowrap; width: 1px">
          <a style="font-weight: bold; cursor: pointer" v-on:click="putBookAuthor(author)">この著者を追加</a>
        </td>
        <td>
          <router-link :to="{ name: 'AuthorsDetailView', params: { authorId: author.id } }">
            {{ author.name }}
          </router-link>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'BooksDetailView',
  data() {
    return {
      title: null,
      dateOfPublication: null,
      authors: [],
      allAuthors: [],
      searchName: null
    }
  },
  computed: {
    detailUrl() {
      return '/books/' + this.$route.params.bookId
    },
    otherThanAuthors() {
      return this.allAuthors.filter(author => !this.authors.map(value => value.id).includes(author.id))
    }
  },
  watch: {
    $route() {
      this.getBook()
      this.getAuthorsByBook()
      this.getAllAuthors()
    }
  },
  mounted() {
    this.getBook()
    this.getAuthorsByBook()
    this.getAllAuthors()
  },
  methods: {
    getBook() {
      this.$axios.get(this.detailUrl)
        .then(response => {
          this.title = response.data.title
          this.dateOfPublication = response.data.dateOfPublication
        }).catch(err => {
          alert('データがありません。:' + err.response.status + err.response.statusText)
        })
    },
    getAuthorsByBook() {
      this.$axios.get(this.detailUrl + '/authors')
        .then(response => {
          this.authors = response.data
        }).catch(err => {
          alert('データがありません。:' + err.response.status + err.response.statusText)
        })
    },
    getAllAuthors() {
      this.$axios.get('/authors', {params: {name: this.searchName}})
        .then(response => {
          this.allAuthors = response.data
        }).catch(err => {
          alert('データがありません。:' + err.response.status + err.response.statusText)
        })
    },
    putTitle() {
      this.$axios.put(this.detailUrl + '/title', this.title, { headers: { 'Content-Type': 'text/plain' } })
        .then(() => {
          alert('タイトルを変更しました。')
        }).catch(err => {
          alert('変更に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    },
    putDateOfPublication() {
      this.$axios.put(
        this.detailUrl + '/date-of-publication',
        this.dateOfPublication,
        { headers: { 'Content-Type': 'text/plain' } }
      ).then(() => {
        alert('出版日を変更しました。')
      }).catch(err => {
        alert('変更に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
      })
    },
    putBookAuthor(author) {
      this.$axios.put(this.detailUrl + '/authors/' + author.id)
        .then(() => {
          this.getAuthorsByBook()
          this.getAllAuthors()
          alert(author.name + 'を著者に追加しました。')
        }).catch(err => {
          alert('追加に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    },
    deleteBook() {
      let select = confirm('この書籍を削除しますか？')
      if (select) {
        this.$axios.delete(this.detailUrl)
          .then(() => {
            alert('削除しました。')
            this.$router.push({ name: 'BooksView' })
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