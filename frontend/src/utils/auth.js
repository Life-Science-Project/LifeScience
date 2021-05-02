export const setTokens = (tokens) => {
    localStorage.setItem('jwtToken', tokens.jwt);
    localStorage.setItem('refreshToken', tokens.refreshToken)
}

export const removeTokens = () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('refreshToken')
}

export const getTokens = () => {
    return{
        jwt: localStorage.getItem('jwtToken'),
        refreshToken: localStorage.getItem('refreshToken')
    }
}