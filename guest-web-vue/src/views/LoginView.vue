<template>
  <div class="page">

    <main class="form-signin">
      <form @submit="login">
        <h1 class="">Kiosk Editor</h1>

        <h2 class="h3 mb-3 fw-normal">Přihlašte se prosím</h2>

        <div class="form-floating">
          <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" required v-model="credentials.email">
          <label for="floatingInput">Email</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPassword" placeholder="Password" required v-model="credentials.password">
          <label for="floatingPassword">Heslo</label>
        </div>

        <p class="text-danger" v-if="invalidCredentials">Špatné přihlašovací údaje</p>


        <button class="w-100 btn btn-lg btn-primary" type="submit">Přihlásit se</button>
        <p class="mt-5 mb-3 text-muted">© 2021 –2022</p>
      </form>
    </main>
  </div>

</template>


<script setup>

import {ref} from "vue";
import {useUserStore} from "@/stores/user";
import router from "@/router";



const userStore = useUserStore()


const credentials = ref({email: "jan.vesely92@gmail.com", password: "secret"})

const invalidCredentials = ref(false)

async function login(e){
  e.preventDefault()

  const hotelId = await userStore.login(credentials.value.email, credentials.value.password)

  console.log(hotelId)

  if (hotelId){
    invalidCredentials.value = false
    router.push({name: "kiosk", params: {id: hotelId}} )
  }else{
    invalidCredentials.value = true
  }

}


</script>

<style>

.page{
  background-color: #F9F9F9;
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}


.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin h1{
  text-align: center;
  margin-bottom: 50px;
}


.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}



</style>