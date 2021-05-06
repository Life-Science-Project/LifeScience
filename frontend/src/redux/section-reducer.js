import {sectionApi} from "../api/method-api";

const initialState = {
    contents: {},
    name: "",
    isReceived: false,
}

const RECEIVE_CONTENTS = "RECEIVE_CONTENTS"

function receiveContents(data) {
    return {
        type: RECEIVE_CONTENTS,
        contents: data.contents,
        name: data.name,
        isReceived: true,
    }
}

export function fetchContents(versionId, sectionId) {
    return dispatch => sectionApi.getSection(versionId, sectionId)
        .then(response => response.data)
        .then(data => dispatch(receiveContents(data)))

}

export default function sectionReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_CONTENTS :
            return {
                ...state,
                contents: action.contents,
                name: action.name,
                isReceived: action.isReceived
            }
        default :
            return state
    }
}



