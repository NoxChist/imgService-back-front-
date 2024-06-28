import axios from "axios";
import {getSub} from "@/utils/authUtils.js";

const API_URL = 'http://localhost:8080/insta'
const ALBUM_POINT = 'album'
const IMG_POINT = 'image'

const MAX_CONTENT_LENGTH = 50 * 1024 * 1024; // 50MB
const TIMEOUT = 10000;


const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    }
});
apiClient.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
class ImagesService {
    async getImgList(page = 0) {
        try {
            const response = await apiClient.get(`/${ALBUM_POINT}`, {
                params: {
                    page: page
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching image list:', error);
            throw error;
        }
    }
    async getUserImg(page = 0, userId) {
        if (localStorage.getItem('token')!=null) {
            try {
                const response = await apiClient.get(`/${ALBUM_POINT}/${userId}`, {
                    params: {
                        page: page
                    }
                });
                return response.data;
            } catch (error) {
                console.error('Error fetching image list:', error);
                throw error;
            }
        }
    }
    async uploadImages (formData){
        const userId = getSub();
        try {
            return await apiClient.post(`/${ALBUM_POINT}/${userId}`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' },
                timeout: TIMEOUT,
                maxContentLength: MAX_CONTENT_LENGTH,
                maxBodyLength: MAX_CONTENT_LENGTH,
            });
        } catch (error) {
            console.error('Error fetching image list:', error);
            throw error;
        }
    }
    getImgUrl (id){
        return `${API_URL}/${IMG_POINT}?author=${id.author}&imgName=${id.imgName}`;
    }
    async remove(id) {
        console.log(id)
        try {
            return await apiClient.delete(`${API_URL}/${IMG_POINT}?author=${id.author}&imgName=${id.imgName}`);
        } catch (error) {
            console.error('Error during delete image: ', error);
            throw error;
        }
    }

}

export default new ImagesService()