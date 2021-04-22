import axios from "axios";

export const instance = axios.create({
    withCredentials: true,
    baseURL: 'https://life-science-2021.herokuapp.com/api/'
});

export const ResultCodesEnum = {
    Success: 0,
    Error: 1
}