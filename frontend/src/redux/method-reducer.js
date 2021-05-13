import {methodApi} from "../api/method-api";

const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'
const CLEAR_SECTIONS = 'CLEAR_SECTIONS'
const RECEIVE_PROTOCOLS = 'RECEIVE_PROTOCOLS'

const initialState = {
    name: "",
    sections: [],
    isReceived: false,
    isMainPage: true,
    articleId: 0,
}

function receiveSections(data) {
    return {
        type: RECEIVE_SECTIONS,
        sections: data.sections,
        name: data.articleName,
        isMainPage: data.protocolId === null,
        articleId: data.articleId
    }
}

export function clearSections() {
    return {
        type: CLEAR_SECTIONS
    }
}

export function fetchSections(versionId) {
    return dispatch => {
        return methodApi.getMethod(versionId)
            .then(response => response.data)
            .then(data => {
                return dispatch(receiveSections(data))
            })
    }
}

export default function methodReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_SECTIONS:
            return {
                ...state,
                name: action.name,
                sections: action.sections,
                isMainPage: action.isMainPage,
                articleId: action.articleId,
                isReceived: true,
            }
        case CLEAR_SECTIONS:
            return  {
                ...state,
                isReceived: false
            }
        default:
            return state
    }
}