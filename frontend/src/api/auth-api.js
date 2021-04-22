import {instance} from './api'

export const authApi = {
    signInUser(data) {
        let url = 'auth/signin';
        return instance.post(url, data);
    },
    signUpUser(data) {
        let url = 'auth/signup';
        return instance.post(url, data)
    }
}

//Пишем в authApi, все методы связанные с запросами связанными с USER