import {instance} from "./api";

export const usersApi = {
    getUser(userId) {
        const url = 'users/' + userId;
        return instance.get(url);
    },
    putToUserData(userId, data) {
        const url = 'users/' + userId;
        return instance.put(url, data);
    },
    getUserFavorites(id) {
        const url = 'users/' + id + '/favourites/';
        return instance.get(url);
    },
    putToUserFavorites(userId, articleId) {
        const url = 'users/' + userId + '/favourites/' + articleId;
        return instance.put(url);
    },
    deleteFromUserFavourites(userId, articleId) {
        const url = 'users/' + userId + '/favourites/' + articleId;
        return instance.delete(url);
    }
}