import {createRouter, createWebHistory} from 'vue-router'
import Main from "@/pages/Main.vue";
import Authorization from "@/pages/Authorization.vue";
import UserAlbum from "@/pages/UserAlbum.vue";
import PublishImage from "@/pages/PublishImage.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: Main,
            props: {myAlbum : false}
        },
        {
            path: '/my-album/:id',
            component: UserAlbum,
            props: {myAlbum : true}
        },
        {
            path: '/album/:id',
            component: UserAlbum,
        },
        {
            path: '/login',
            component: Authorization,
            props: {type : 'login'}
        },
        {
            path: '/registration',
            component: Authorization,
            props: {type : 'registration'}
        },
        {
            path: '/publish',
            component: PublishImage,
        }
    ]
})

export default router