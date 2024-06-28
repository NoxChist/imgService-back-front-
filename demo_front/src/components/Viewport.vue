<script>
import ImageService from "@/service/ImageService.js";
import {VueAwesomePaginate} from "vue-awesome-paginate";
import {getSub, isAdmin} from "@/utils/authUtils.js";
import {HttpStatusCode} from "axios";

export default {
  components: {VueAwesomePaginate},
  data(){
    return {
      currentPage: 1,
      total: 10,
    }
  },
  props: {
    images: {
      type: Array,
      return: true
    },
    size: {
      type: Number,
      return: true
    },
    totalPages: {
      type: Number,
      return: true
    },
    myAlbum: {
      type: Boolean,
      required: true
    },
    login: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    getImage(id)  {
      return ImageService.getImgUrl(id)
    },
    onClickHandler(newPage) {
      this.currentPage = newPage;
      this.$emit('updatePage', newPage);
    },
    showDelBtn(){
      if(this.login){
        if(this.myAlbum) {
          if(this.images.length>0){
            const author = this.images[0].id.author;
            const sub = getSub();
            return author === sub;
          }
        }
        return !!isAdmin();
      }
      return false;
    },
    async remove(id) {
      const response = await ImageService.remove(id);
      if(response.status===HttpStatusCode.Ok){
        this.$emit('imageRemoved', id);
        alert('Картинка была успешна удалена');
      }
    }
  },
  watch: {
    currentPage(newPage) {
      this.$emit('updatePage', newPage);
    }
  },
}

</script>

<template>
  <div class="viewport">
    <div class="row g-0 info">На странице {{size}} изображений</div>
    <div class="row g-0 info"  style="margin-bottom: 2vh" v-if="totalPages > 0">
      <div class="container">
        <vue-awesome-paginate
            :total-items="totalPages"
            v-model="currentPage"
            :items-per-page="1"
            :max-pages-shown="5"
            :show-breakpoint-buttons="false"
            @click="onClickHandler"
        />
      </div>
    </div>
    <div class="card image"
         v-for="img in images"
         :key="img.id" >
      <div class="card-img-top">
        <img :src="getImage(img.id)" alt="...">
      </div>
      <div class="card-footer">
        Автор: {{img.id.author}}<br>Дата: {{img.publicationDate}}
      </div>
      <div class="row g-0 admin" v-if="showDelBtn()">
        <div class="col" @click="remove(img.id)">Удалить</div>
      </div>
    </div>
  </div>
</template>

<style>
.test {
  width: 100%;
}
.viewport {
  width: 50vw;
  min-width: 300px;
  position: relative;
}
.viewport>* {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.info {
  height: 50px;
  flex-direction: row;
  justify-content: center;
  align-content: center;
}
.viewport .container{
  display: flex;
  justify-content: center;
  height: max-content;
   ul {
     margin: 0;
     height: max-content;
   }

}
.viewport .card{
  border-radius: 0;
  border: 1px grey solid;
}
.card-footer {
  background-color: white!important;
  text-align: center;
}
.paginate-buttons {
  zoom: 0.86;
}
.card-img, .card-img-top, .card-footer{
  display: flex;
  justify-content: center;
}

.image {
  width: 100%
}
.image img {
  max-width: 100%;
}
.admin {
  justify-content: center;
  margin-bottom: 6px;
}
.admin .col {
  max-width: 100px;
  font-weight: 600;
  text-align: center;
  background-color: #a8212e;
  color: white;
  border: 2px #a247479c outset;
  cursor: pointer;
}
.admin .col:hover{
  background-color: #ac2d35;
  transform: scale(1.08);
  transition: 0.4ms;
}

.pagination-container {
  display: flex;
  column-gap: 10px;
}
.paginate-buttons {
  height: 40px;
  width: 40px;
  border-radius: 20px;
  cursor: pointer;
  background-color: rgb(242, 242, 242);
  border: 1px solid rgb(217, 217, 217);
  color: black;
}

.paginate-buttons:hover {
  background-color: #d8d8d8;
}

.active-page {
  background-color: #3498db;
  border: 1px solid #3498db;
  color: white;
}

.active-page:hover {
  background-color: #2988c8;
}
</style>