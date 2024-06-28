import axios from 'axios'


const API_URL = 'http://localhost:8080';
const SIGN_IN = '/sign-in';
const REG = '/register';
const SIGN_IN_ERROR_MSG = 'Error during login: ';
const REG_ERROR_MSG = 'Error during registration: ';

const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

class BaseService {
    constructor(endpoint, errorMsg) {
        this.url =`${API_URL}${endpoint}`;
        this.errorMsg = errorMsg;
    }
    /*async auth (data) {
        try {
            return await apiClient.post(this.url, {
                email: data.login,
                password: data.password
            });
        } catch (error) {
            console.error(this.errorMsg, error);
        }
    }*/
    async auth(data) {
        try {
            return await apiClient.post(this.url, data);
        } catch (error) {
            console.error(this.errorMsg, error);
            throw error;
        }
    }


}

class RegistrationService extends BaseService{
    constructor() {
        super(REG, REG_ERROR_MSG)
    }

}
class SingInService extends BaseService{
    constructor() {
        super(SIGN_IN, SIGN_IN_ERROR_MSG)
    }
}
class AuthService {
    static getService(endpoint) {
        switch (endpoint) {
            case 'registration':
                return new RegistrationService();
            case 'login':
                return new SingInService();
            default:
                throw new Error('Unknown service type.');
        }
    }
}
export default AuthService;