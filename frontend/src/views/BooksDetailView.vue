<template>
  <div>
    <h3>書籍基本情報</h3>
    <p>
      <label>
        タイトル
        <input type="text" placeholder="書籍タイトルを入力" v-model="title">
      </label>
    </p>
    <p>
      <label>
        出版日
        <input type="date" placeholder="yyyy-MM-dd" v-model="dateOfPublication">
      </label>
    </p>
    <p>
      <button v-on:click="putBook">更新</button>
    </p>
    <p>
      <label>
        著者：
        <label v-for="author in authors" :key="author.id">
          <router-link :to="{ name: 'AuthorsDetailView', params: { authorId: author.id } }">
            {{ author.name }}
          </router-link>
          <a class="delete" v-on:click="deleteBookAuthor(author)">除外</a>
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
          <a class="add" v-on:click="putBookAuthor(author)">この著者を追加</a>
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
    putBook() {
      this.$axios.put(this.detailUrl, {
        id: this.$route.params.bookId,
        title: this.title,
        dateOfPublication: this.dateOfPublication
      }).then(() => {
        alert('書籍情報を更新しました。')
      }).catch(err => {
        alert('変更に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
      })
    },
    putBookAuthor(author) {
      this.$axios.put(this.detailUrl + '/authors/' + author.id)
        .then(() => {
          this.getAuthorsByBook()
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
    },
    deleteBookAuthor(author) {
      let select = confirm('この著者を除外しますか？')
      if (select) {
        this.$axios.delete(this.detailUrl + '/authors/' + author.id)
          .then(() => {
            this.getAuthorsByBook()
            alert(author.name + 'をこの書籍の著者から除外しました。')
          }).catch(err => {
            alert('除外に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
          })
      }
    },
  }
}
</script>

<style scoped>
a.add {
  font-weight: bold;
  cursor: pointer;
}
a.delete {
  font-weight: bold;
  cursor: pointer;
  color: #f37b00;
}
</style>