import {sectionApi} from "../api/method-api";

const RECEIVE_CONTENTS = "RECEIVE_CONTENTS"
const CLEAR_CONTENTS = "CLEAR_CONTENTS"

const initialState = {
    contents: {},
    name: "",
    isReceived: false,
}

export function clearContents() {
    return {
        type: CLEAR_CONTENTS,
    }
}

function receiveContents(data) {
    return {
        type: RECEIVE_CONTENTS,
        contents: data.contents,
        name: data.name,
    }
}

export function fetchContents(versionId, sectionId) {
    return dispatch => {
        sectionApi.getSection(versionId, sectionId)
            .then(response => response.data)
            .then(data => dispatch(receiveContents(data)))
    }

}

export default function sectionReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_CONTENTS :
            return {
                ...state,
                contents: action.contents,
                name: action.name,
                isReceived: true,
            }
        case CLEAR_CONTENTS: {
            return {
                ...state,
                isReceived: false,
            }
        }
        default :
            return state
    }
}



