<!--Authorization.vue-->
<script>

import {HttpStatusCode} from "axios";
import AuthService from "@/service/authService.js";
/*import {EventBus} from "@/utils/event-bus.js";*/

export default {
  data (){
    return {
      email:'',
      password:'',
      firstname:'',
      lastname:''
    }
  },
  props: {
    type: String,
    required: true
  },
  methods: {
    async submit() {
      const authData = this.isRegistration() ? {
        firstname: this.firstname,
        lastname: this.lastname,
        email: this.email,
        password: this.password
      } : {
        email: this.email,
        password: this.password
      };

      if (Object.values(authData).every(value => value !== '')) {
        const authService = AuthService.getService(this.type);

        try {
          const response = await authService.auth(authData);

          let successMSG = this.type === 'login' ? 'Вы успешно авторизовались на сайте.' : 'Вы успешно зарегистрировались на сайте.';
          let errorMSG = this.type === 'login' ? 'Ошибка авторизации: ' : 'Ошибка при регистрации: ';

          if (response.status === HttpStatusCode.Ok) {
            alert(successMSG);
            localStorage.setItem('token', response.data.token);
            let authorised = true;
            this.emitter.emit("authorised", authorised)
            this.$router.push('/');

          } else {
            alert(errorMSG + response.status);
          }
        } catch (error) {
          alert('Произошла ошибка: ' + error.message);
        }
      }
    },
    isRegistration() {
      return this.type === "registration";
    }
  }
}
</script>

<template>
  <div class="form">
    <div class="row g-0" v-if="isRegistration()">
      <div class="col-sm log">
        <label for="firstname">Имя</label>
      </div>
      <div class="col-sm log">
        <input v-model="firstname" id="firstname" type="text"/>
      </div>
    </div>
    <div class="row g-0" v-if="isRegistration()">
      <div class="col-sm log">
        <label for="lastname">Фамилия</label>
      </div>
      <div class="col-sm pass">
        <input v-model="lastname" id="lastname" type="text"/>
      </div>
    </div>
    <div class="row g-0">
      <div class="col-sm log">
        <label for="email">Email</label>
      </div>
      <div class="col-sm pass">
        <input v-model="email" placeholder=" myworkmail@o2rus.ru" id="email" type="text" required/>
      </div>
    </div>
    <div class="row g-0">
      <div class="col-sm log">
        <label for="password">Пароль</label>
      </div>
      <div class="col-sm pass">
        <input v-model="password" id="password" type="text" required/>
      </div>
    </div>

    <div class="row g-0" style="justify-content: flex-end">
      <button class="submit" v-on:click="submit">
        {{ type === 'login' ? 'Войти' : 'Зарегистрироваться' }}
      </button>
    </div>
  </div>

</template>

<style scoped>
label {
  width: 60px;
  padding: 0;
  height: 100%;
  display: flex;
  align-content: center;
  align-items: center;
}
.form {
  align-items: center;
  min-height: 30vh;
  width: 220px;
  justify-content: center;
}
.form .row {
  width: 100%;
  flex-direction: column;
}
input {
  width: -webkit-fill-available;
}
.submit {
  margin: 20px 0;
  padding: 4px 30px;
  color: white;
  background-color: #ce211e;
  border-radius: 2px;
  border: 1px #6f0c0b solid;
  font-weight: 600;
}
</style>