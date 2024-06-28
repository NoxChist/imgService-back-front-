<template>
  <div class="row g-0">
    <div class="tooltip-container" style="zoom: 0.6">
      <button class="tooltip-button my_btn" v-on:click="addPhotos()">
        <img src="../assets/icon/img.png">
      </button>
      <span class="tooltip-text">Добавить фото</span>
    </div>
  </div>
  <input type="file" id="images" ref="images" multiple accept=".jpg, .jpeg, .png" v-on:change="handlePhotosUpload()" required/>
  <div class="images-bar">
    <div v-for="(image, key) in images" :key="key" class="file-listing">
      <div class="nav-row">
        <div class="remove-img" v-on:click="removeImg(key)">X</div>
      </div>
      <img :src="getImageURL(image)">
    </div>
  </div>
  <div class="row g-0"  v-if="images.length > 0">
    <div class="tooltip-container">
      <button class="tooltip-button my_btn" v-on:click="submit()">
        <h5>Готово</h5>
      </button>
    </div>
  </div>
</template>
<script>
import {getSub} from "@/utils/authUtils.js";
import imageService from "@/service/ImageService.js";
import {HttpStatusCode} from "axios";

export default {
  data() {
    return {
      images: [],
    }
  },
  methods: {
    addPhotos() {
      this.$refs.images.click();
    },
    handlePhotosUpload() {
      this.images = Array.from(this.$refs.images.files);
    },
    removeImg(key) {
      if (this.images.length === 0 || this.image === this.images[key].name) {
        this.image = '';
      }
      this.images.splice(key, 1);
    },
    getImageURL(image) {
      return URL.createObjectURL(image);
    },
    async submit() {
      let formData = new FormData();
      for (let i = 0; i < this.images.length; i++) {
        formData.append('images', this.images[i]);
      }
      const response = await imageService.uploadImages(formData)
      if (response.status === HttpStatusCode.Ok) {
        alert(response.data)
        this.$router.push(`/my-album/${getSub()}`)
      }else {
        alert("Something went wrong!:(")
        window.location.href = window.location.href;
      }
    }
  }
}
</script>

<style>
#images {
  display: none;
}
.nav-row {
  display: flex;
  justify-content: flex-end;
  padding: 0 4px;
}
.images-bar {
  width: 90%;
  margin: 5vh 0;
  display: grid;
  justify-items: center;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}
div.file-listing {
  width: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.my_btn {
  padding: 0;
  border-radius: 20%;
  border: none;
  width: 100px;
  height: 100px;
  position: relative;
  background-color: white;
  h5:hover {
    color: #3363b5;
    transform: scale(1.3);
    transition: 0.6ms;
  }
}
img {
  object-fit: cover;
  height: -webkit-fill-available;
}
.remove-img, .main-img {
  color: black;
  cursor: pointer;
  float: right;
}
.remove-img:hover, .main-img:hover {
  color: red;
}
.tooltip-container {
  position: relative;
  display: inline-block;
}

.tooltip-button {
  cursor: pointer;
}

.tooltip-text {
  visibility: hidden;
  width: 140px;
  background-color: #3363b5;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px;
  position: absolute;
  z-index: 1;
  bottom: 90%;
  left: 170%;
  margin-left: -70px;
  opacity: 0;
  transition: opacity 0.3s;
}

.tooltip-container:hover .tooltip-text {
  visibility: visible;
  opacity: 1;
}
</style>