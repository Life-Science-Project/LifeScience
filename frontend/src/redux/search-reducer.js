import {searchApi} from "../api/seacrh-api";

const initialState = {
    results: [],
}

const RECEIVE_SEARCH_RESULTS = "RECEIVE_SEARCH_RESULTS"

function receiveSearchResults(data) {
    return {
        type: RECEIVE_SEARCH_RESULTS,
        results: data
    }
}

export function search(query) {
    return dispatch => searchApi.search(query)
        .then(response => response.data)
        .then(data => dispatch(receiveSearchResults(data)))
}

export default function searchReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_SEARCH_RESULTS :
            return {
                ...state,
                results: action.results,
            }
        default :
            return state
    }
}