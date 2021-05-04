import {authApi} from "../api/auth-api";
import {initApi} from "../api/init-api";
import {getInitData} from "./init-reducer";
import {getTokens, removeTokens, setTokens} from "../utils/auth";
import {errorHandler} from "../utils/errorHandler";

const LOGOUT = 'logout';
const SIGN_IN = 'signin';
const SIGN_UP = 'signup';
const ERROR = 'error';

let initialState = {
    user: null,
    isAuthorized: false,
    errorMsg: ""
}

const authReducer = (state = initialState, action) => {

    switch (action.type) {
        case SIGN_IN:
            return {
                ...state,
                user: action.user,
                isAuthorized: true
            };
        case SIGN_UP:
            return {
                ...state,
                status: true
            }
        case ERROR:
            return {
                ...state,
                errorMsg: action.errorMsg
            }
        case LOGOUT:
            return {
                ...state,
                user: null,
                isAuthorized: false
            }
        default:
            return state;
    }
}

export const error = (_errorMsg) => {
    return {type: ERROR, errorMsg: _errorMsg};
}

export const signInUser = (_user) => {
    return {type: SIGN_IN, user: _user};

}

export const getAuthorizedUserThunk = () => async (dispatch) => {
    let response = await initApi.getAuthorizedUser();
    let errorMsg = "";
    if (response.status === 401) {
        response = await authApi.refreshTokens(getTokens());
        if (response.status === 200) {
            const result = response.data.user;
            setTokens(response.data.tokens);
            dispatch(signInUser(result));
        } else {
            dispatch(logoutUser());
        }
    } else if (response.status === 200) {
        dispatch(signInUser(response.data));
    } else {
        removeTokens();
        errorMsg = errorHandler(response).message;
        dispatch(error(errorMsg));
    }
    dispatch(getInitData({}));
}

export const signInUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signInUser(input);
    let result, errorMsg = "";
    if (response.status === 200) {
        result = response.data.user;
        setTokens(response.data.tokens);
        dispatch(signInUser(result));
    } else {
        errorMsg = errorHandler(response).message;
        dispatch(error(errorMsg));
    }
}

export const signUpUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signUpUser({
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        password: input.password
    });
    let result, errorMsg = "";
    if (response.status === 200) {
        result = response.data.user;
        setTokens(response.data.tokens);
        dispatch(signInUser(result));
    } else {
        errorMsg = errorHandler(response).message;
        dispatch(error(errorMsg))
    }
}

export const logoutUser = () => {
    removeTokens();
    return {type: LOGOUT}
}

export default authReducer;
