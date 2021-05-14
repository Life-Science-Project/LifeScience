import {authApi} from "../api/auth-api";
import {initApi} from "../api/init-api";
import {getTokens, removeTokens, setTokens} from "../utils/auth";

const LOGOUT = 'logout';
const SIGN_IN = 'signin';
const SIGN_UP = 'signup';
const ERROR = 'error';
const UNDEFINED_USER = 'undefineduser'

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
        case UNDEFINED_USER:
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

export const undefinedUser = () => {
    return {type: UNDEFINED_USER}
}

export const getAuthorizedUserThunk = () => async (dispatch) => {
    let tokens = getTokens();

    if (!tokens.jwt) {
        dispatch(undefinedUser())
        return;
    }

    let response = await initApi.getAuthorizedUser();

    if (response.status === 200) {
        dispatch(signInUser(response.data));
        return;
    }

    if (response.status === 401) {
        response = await authApi.refreshTokens(getTokens());
        if (response.status === 200) {
            const result = response.data.user;
            setTokens(response.data.tokens);
            dispatch(signInUser(result));
            return;
        }

        dispatch(logoutUser());
        return;
    }

    removeTokens();
    dispatch(error(response.message));
}

export const signInUserThunk = (input) => async (dispatch) => {
    let response = await authApi.signInUser(input);

    if (response.status === 200) {
        setTokens(response.data.tokens);
        dispatch(signInUser(response.data.user));
        return;
    }

    dispatch(error(response.message));
}

export const signUpUserThunk = (input) => async (dispatch) => {
    const userData = {
        firstName: input.firstName,
        lastName: input.lastName,
        email: input.email,
        password: input.password
    };

    const response = await authApi.signUpUser(userData);

    if (response.status === 200) {
        setTokens(response.data.tokens);
        dispatch(signInUser(response.data.user));
        return;
    }

    dispatch(error(response.message))
}

export const logoutUser = () => {
    removeTokens();

    return {type: LOGOUT}
}

export default authReducer;
