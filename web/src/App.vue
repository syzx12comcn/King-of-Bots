<template>
  <div>
    <NavBar />
  </div>
  <router-view></router-view>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'

export default{
  components:{
    NavBar
  },
  setup(){
    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "hah",
    //     password: "pppp",
    //     confirmedPassword: "pppp",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });
    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/info/",
    //   type: "get",
    //   headers: {
    //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2Y2NjMGNjZGQwYjg0NzgyOGYzZjAwNTVkZTA3YmUyYSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY2MjQ3MTczNiwiZXhwIjoxNjYzNjgxMzM2fQ.W6yLpb5gQHCylTVN6uP_DyTi8Q_lnWQ906wy3SuJmt4"
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // })
  }

}
</script>

<style>
body {
  background-image: url("./assets//images/bg.jpeg");
  background-size: cover;
}
</style>
