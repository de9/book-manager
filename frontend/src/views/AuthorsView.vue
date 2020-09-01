<template>
  <div>
    <label>
      <input type="text" placeholder="著者氏名を入力" v-model="name">
      <button v-on:click="getAuthors">検索</button>
    </label>
    <table>
      <thead>
      <tr>
        <th>氏名</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="author in authors" :key="author.id">
        <td>{{ author.name }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'AuthorsView',
  data() {
    return {
      name: null,
      authors: []
    }
  },
  mounted() {
    this.getAuthors()
  },
  methods: {
    async getAuthors() {
      let response = await this.$axios.get('/authors', {params: {name: this.name}})
      this.authors = response.data
    }
  }
}
</script>

<style scoped>

</style>