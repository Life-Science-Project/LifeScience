import {instance} from "./api";

export const initApi = {
    getAuthorizedUser() {
        const uri = 'users/current';
        return instance.get(uri);
    }
}

