import axios from "axios";

const instance = axios.create({
    withCredentials: true,
    baseURL: 'https://life-science-2021.herokuapp.com/api/',
    headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
    }
});

instance.interceptors.response.use(
    (response) =>  response,
    (error) => {
        const statusCode = error.response ? error.response.status : null;
        if (statusCode === 401) {
            return {status: statusCode, message: "Try to refresh jwtToken, or you haven't permission"}
        }
        if (statusCode === null) {
            return {status: null, message: "All bad"}
        }
        return {status: statusCode, message: "Wrong request"}
    }
);

export default instance;