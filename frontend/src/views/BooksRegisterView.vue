<template>
  <div>
    <p>
      <label>
        タイトル
        <input type="text" placeholder="書籍タイトルを入力" v-model="title">
      </label>
    </p>
    <p>
      <label>
        出版日
        <input type="date" placeholder="書籍出版日を入力" v-model="dateOfPublication">
      </label>
    </p>
    <p>
      <label>
        <button :disabled="disabled" v-on:click="postBooks">登録</button>
      </label>
    </p>
  </div>
</template>

<script>
export default {
  name: 'BooksRegisterView',
  data() {
    return {
      title: null,
      dateOfPublication: null
    }
  },
  computed: {
    disabled() {
      return !this.title || !this.dateOfPublication
    }
  },
  methods: {
    postBooks() {
      this.$axios.post('/books', {id: 0, title: this.title, dateOfPublication: this.dateOfPublication})
        .then(() => {
          alert(this.title + 'を追加しました。')
          this.title = null
          this.dateOfPublication = null
        }).catch(err => {
          alert('追加に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    }
  }
}
</script>

<style scoped>

</style>