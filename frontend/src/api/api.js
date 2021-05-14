import axios from "axios";
import {getTokens} from "../utils/auth";
import {errorHandler} from "../utils/errorHandler";

const instance = axios.create({
    withCredentials: true,
    baseURL: 'https://life-science-2021.herokuapp.com/api/'
});

instance.interceptors.response.use(
    (response) =>  response,
    (error) => {
        console.log(JSON.stringify(error))
        const statusCode = error.response ? error.response.status : null;
        if (statusCode === null) {
            return {status: null, message: "All bad"}
        }
        return errorHandler(error);
    }
);

instance.interceptors.request.use(function (config) {
    const tokens = getTokens();
    if (tokens.jwt) {
        config.headers.Authorization =  'Bearer ' + tokens.jwt;
    }
    return config;
});

export default instance;
