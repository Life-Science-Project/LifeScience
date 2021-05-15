import {categoryApi} from "../api/category-api";

const EMPTY = 'empty';
const GET_CATEGORY = 'GET_CATEGORY';
const NOT_FOUND_CATEGORY = 'NOT_FOUND_CATEGORY';
const CLEAR_CATEGORY = "CLEAR_CATEGORY"

let initialState = {
    category: null,
    status: EMPTY,
    isReceived: false,
};

const categoryReducer = (state = initialState, action) => {

    switch (action.type) {
        case GET_CATEGORY:
            return {
                ...state,
                category: action.category,
                isReceived: true,
            };
        case NOT_FOUND_CATEGORY:
            return {
                ...state,
                trouble: action.trouble,
                isReceived: false,
            }
        case CLEAR_CATEGORY:
            return {
                ...state,
                isReceived: false,
            }
        default:
            return state;
    }
}

export const getCategory = (_id) => {
    return {type: GET_CATEGORY, category: _id}
}

export const clearCategory = () => {
    return {
        type: CLEAR_CATEGORY,
    }
}

export const getError = (trouble, reason) => {
    return {type: reason, trouble: trouble}
}


export const getCategoryThunk = (id) => async (dispatch) => {
    let response = await categoryApi.getCategory(id);
    if (response.status === 404) {
        dispatch(getError(response.data, NOT_FOUND_CATEGORY))
    }
    dispatch(getCategory(response.data))
}

export default categoryReducer;