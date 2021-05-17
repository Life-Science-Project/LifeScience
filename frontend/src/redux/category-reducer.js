import {
    CLEAR_CATEGORY,
    EMPTY,
    ERROR,
    GET_CATEGORY
} from "./actions/category-actions";

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
                error: null,
                isReceived: true
            };
        case ERROR:
            return {
                ...state,
                error: action.error,
                isReceived: false,
            }
        case CLEAR_CATEGORY:
            return {
                ...state,
                isReceived: false,
                category: null,
            }
        default:
            return state;
    }
}

export default categoryReducer;
