<!--Navbar.vue-->

<script>
import {getSub, isAdminOrUser} from "@/utils/authUtils.js";

export default {
  data() {
    return {
      login: isAdminOrUser(),
    }
  },
  methods: {
    toMyAlbum(){
      const id = getSub();
      this.$router.push(`/my-album/${id}`);
    },
    logout(){
      if(localStorage.getItem('token')!=null){
        localStorage.removeItem('token')
        let authorised = false;
        this.emitter.emit("authorised", authorised)
      }
    },
    addNew() {
      this.$router.push('/publish')
    }
  },
  mounted() {
    this.emitter.on("authorised", authorised => {
      this.login = authorised;
    });
  }
}
</script>

<template>
  <nav class="navbar">
    <div class="row g-0">
      <div class="col-6 left">
        <div class="col">
          <h5 @click="$router.push('/')">На главную</h5>
        </div>
        <div class="col" >
          <h5 @click="toMyAlbum" v-if="login">Мой альбом</h5>
          <div class="add" v-if="login">
            <h2 @click="addNew">+</h2>
          </div>
        </div>
      </div>

      <div class="col-6 right" v-if="!login">
        <div class="col">
          <h5 @click="$router.push('/login')">Войти</h5>
        </div>
        <div class="col">
          <h5 @click="$router.push('/registration')">Зарегистрироваться</h5>
        </div>
      </div>
      <div class="col-6 right" style="justify-content: flex-end;" v-if="login">
        <div class="col">
          <h5 @click="logout">Выйти</h5>
        </div>
      </div>

    </div>
  </nav>
</template>

<style scoped>
nav {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 4vh;

}
.row {
  max-width: 800px;
  flex-wrap: nowrap;
  flex-direction: row;
  width: -webkit-fill-available;
  height: 8vh;
  position: relative;
  align-items: center;
  justify-content: space-between;
}
.navbar .col-6 {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-around;
}
nav .col {
  width: 130px;
  max-width: 130px;
  white-space: nowrap;
  display: flex;
  justify-content: center;
  position: relative;
   h5 {
     font-size: 1.1rem;
     cursor: pointer;
   }
  h5:hover{
    color: #b32735;
    border-bottom: #b32735 2px solid;
  }
}
.add {
  display: flex;
  align-content: flex-start;
  position: absolute;
  top: -16px;
  right: -16px;
  cursor: pointer;
}
.add:hover {
  color: #b32735;
  transform: scale(1.1);
}


</style>