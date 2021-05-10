import instance from './api'

export const statisticsApi = {
    getUsers() {
        const url = 'users/';
        return instance.get(url);
    }
}