import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import "bootstrap/dist/css/bootstrap.min.css"


import "https://api.mapy.cz/loader.js"



const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
