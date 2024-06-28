import '@/assets/main.css';
import { createApp } from 'vue';
import App from './App.vue';
import router from "@/router/router.js";
import VueAwesomePaginate from "vue-awesome-paginate";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "vue-awesome-paginate/dist/style.css";
import mitt from 'mitt';


const emitter = mitt();
const app = createApp(App);
app.use(router);
app.use(VueAwesomePaginate);
app.config.globalProperties.emitter = emitter;
app.mount('#app');
