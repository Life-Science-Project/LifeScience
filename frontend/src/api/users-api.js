import {instance} from "./api";

export const usersApi = {
    getUser(id) {
        const url = 'users/' + id;
        return instance.get(url);
    },
    getUserFavorites(id) {
        const url = 'users/' + id + '/favourites/';
        return instance.get(url);
    }
}