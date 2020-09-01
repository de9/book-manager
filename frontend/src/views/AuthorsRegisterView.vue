<template>
  <div>
    <p>
      <label>
        氏名
        <input type="text" placeholder="著者氏名を入力" v-model="name">
      </label>
    </p>
    <p>
      <label>
        <button :disabled="disabled" v-on:click="postAuthors">登録</button>
      </label>
    </p>
  </div>
</template>

<script>
export default {
  name: 'AuthorsRegisterView',
  data() {
    return {
      name: null
    }
  },
  computed: {
    disabled() {
      return !this.name
    }
  },
  methods: {
    postAuthors() {
      this.$axios.post('/authors', {id: 0, name: this.name})
        .then(() => {
          alert(this.name + 'を追加しました。')
          this.name = null
        }).catch(err => {
          alert('追加に失敗しました。:' + err.response.status + ' ' + err.response.statusText)
        })
    }
  }
}
</script>

<style scoped>

</style>