import axios from "axios";

export const instance = axios.create({
    withCredentials: true,
    baseURL: 'https://life-science-2021.herokuapp.com/api/',
    headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
    }
});

export const ResultCodesEnum = {
    Success: 0,
    Error: 1
}