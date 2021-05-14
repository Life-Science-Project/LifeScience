import {methodApi} from "../api/method-api";

const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'
const CLEAR_SECTIONS = 'CLEAR_SECTIONS'
const PASS_SECTION_ID = "PASS_SECTION_ID"
const ERROR = "ERROR"

const initialState = {
    name: "",
    sections: [],
    isReceived: false,
    isMainPage: true,
    articleId: 0,
    passedSectionId: null,
    protocolName: "",
}

export function passSectionId(sectionId) {
    return {
        type: PASS_SECTION_ID,
        passedSectionId: sectionId,
    }
}

function receiveSections(data) {
    return {
        type: RECEIVE_SECTIONS,
        sections: data.sections,
        name: data.articleName,
        isMainPage: data.protocolId === null,
        articleId: data.articleId,
        protocolName: data.protocolName,
    }
}

export function clearSections() {
    return {
        type: CLEAR_SECTIONS
    }
}

export function getError(_error) {
    return {
        type: ERROR,
        error: _error
    }
}

export const fetchSections = (versionId) => async (dispatch) => {
    const response = await methodApi.getMethod(versionId);

    if (response.status !== 200) {
        dispatch(getError(response));
        return;
    }

    dispatch(receiveSections(response.data));
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
                protocolName: action.protocolName,
            }
        case CLEAR_SECTIONS:
            return  {
                ...state,
                isReceived: false,
                passedSectionId: null,
                error: null
            }
        case PASS_SECTION_ID:
            return {
                ...state,
                passedSectionId: action.passedSectionId,
            }
        case ERROR:
            return  {
                ...state,
                isReceived: true,
                passedSectionId: null,
                error: action.error
            }
        default:
            return state
    }
}
