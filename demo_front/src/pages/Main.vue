<template>
  <viewport
      :images="images"
      :size="size"
      :total-pages="totalPages"
      :my-album="myAlbum"
      :login="login"
      @updatePage = "handlePageChange"
      @imageRemoved="handleImageRemoved"
  />
</template>

<script>
import ImageService from "@/service/ImageService.js";
import Viewport from "@/components/Viewport.vue";
import {isAdminOrUser} from "@/utils/authUtils.js";

export default {
  components: {Viewport},
  data(){
    return {
      images: [],
      size: 0,
      totalPages:0,
      login: isAdminOrUser(),
    }
  },
  props: {
    myAlbum: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    async fetchEntities(page){
      try {

        const data = await ImageService.getImgList(page);
        this.size = data.size;
        this.totalPages = data.totalPages.valueOf();
        this.images = data.content;
      } catch (error) {
        console.error('Error fetching images:', error);
      }
    },
    handlePageChange(newPage) {
      this.fetchEntities(newPage - 1);
    },
    async handleImageRemoved(id) {
      this.images = this.images.filter(image => image.id !== id);
    },
  },
  async created() {
    await this.fetchEntities(0);
  },
  mounted() {
    this.emitter.on("authorised", authorised => {
      this.login = authorised;
    });
  }
}
</script>