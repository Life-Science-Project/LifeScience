import instance from "./api";

export const usersApi = {
    getUser(userId) {
        const url = 'users/' + userId;
        return instance.get(url);
    },
    patchToUserData(userId, data) {
        const url = 'users/' + userId;
        return instance.patch(url, data);
    },
    getUserFavorites(id) {
        const url = 'users/' + id + '/favourites/';
        return instance.get(url);
    },
    patchToUserFavorites(userId, articleId) {
        const url = 'users/' + userId + '/favourites/' + articleId;
        return instance.patch(url);
    },
    deleteFromUserFavourites(userId, articleId) {
        const url = 'users/' + userId + '/favourites/' + articleId;
        return instance.delete(url);
    }
}