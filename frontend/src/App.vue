<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/books">書籍検索</router-link> |
      <router-link to="/authors">著者検索</router-link> |
      <router-link to="/books/register">書籍登録</router-link> |
      <router-link to="/authors/register">著者登録</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

@media (prefers-color-scheme: dark) {
  #app {
    color: #d6e5fd;
  }
  a {
    color: #aacaf8;
  }
  #nav a {
    color: #d6e5fd;
  }
  #nav a.router-link-exact-active {
    color: #a5fdcf;
  }
}
</style>
