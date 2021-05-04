import instance from './api'

export const authApi = {
    signInUser(data) {
        let url = 'auth/signin';
        return instance.post(url, data);
    },
    signUpUser(data) {
        let url = 'auth/signup';
        return instance.post(url, data)
    },
    refreshTokens(tokens) {
        let url = 'auth/refresh';
        return instance.post(url, tokens);
    }
}