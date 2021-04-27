
import {methodApi} from "../api/method-api";

export const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'

const initialState = {
    name: "",
    sections: [],
    versionId: 1
}

function receiveSections(data) {
    return {
        type: RECEIVE_SECTIONS,
        sections: data.version.sections,
        name: data.version.name,
        versionId: data.id
    }
}

export function fetchSections(articleId) {
    return dispatch => {
        return methodApi.getMethod(articleId)
            .then(response => response.data)
            .then(data => dispatch(receiveSections(data)))
    }
}

export default function methodReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_SECTIONS:
            return {
                ...state,
                name: action.name,
                sections: action.sections,
                versionId: action.versionId
            }
        default:
            return state
    }
}

