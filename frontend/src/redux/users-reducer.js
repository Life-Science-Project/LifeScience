import {usersApi} from "../api/users-api";

const GET_USER = 'GET_USER';
const ERROR = 'USER_ERROR';
const GET_USER_FAVOURITES = 'GET_USER_FAVOURITES';

const initialState = {
    user: null,
    userFavourites: []
}

const usersReducer = (state = initialState, action) => {

    switch (action.type) {
        case ERROR:
            return {
                ...state,
                trouble: action.trouble
            };
        case GET_USER:
            return {
                ...state,
                user: action.user
            };
        case GET_USER_FAVOURITES:
            return {
                ...state,
                userFavourites: action.userFavourites
            }
        default:
            return {
                ...state
            };
    }
};

export const getError = (trouble, reason) => {
    return {type: reason, trouble: trouble}
};

export const getUser = (user) => {
    return {type: GET_USER, user: user}
};

export const getUserFavourites = (favourites) => {
    return {type: GET_USER_FAVOURITES, userFavourites: favourites}
};

export const getUsersThunk = (id) => async (dispatch) => {
    let response = await usersApi.getUser(id);
    if (response.status !== 200) {
        dispatch(getError(response.data, ERROR))
    }
    dispatch(getUser(response.data));
};

export const getUserFavouritesThunk = (id) => async (dispatch) => {
    let response = await usersApi.getUserFavorites(id);
    if (response.status === 200) {
        dispatch(getError(response.data, ERROR))
    }
    dispatch(getUserFavourites(response.data));
};

export default usersReducer;
