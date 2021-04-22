import axios from "axios";

export const instance = axios.create({
    withCredentials: true,
    baseURL: 'http://localhost:8080/api/'
});

export const ResultCodesEnum = {
    Success: 0,
    Error: 1
}