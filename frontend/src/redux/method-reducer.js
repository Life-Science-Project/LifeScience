import {methodApi} from "../api/method-api";

const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'
const CLEAR_SECTIONS = 'CLEAR_SECTIONS'
const PASS_SECTION_ID = "PASS_SECTION_ID"

const initialState = {
    name: "",
    sections: [],
    isReceived: false,
    isMainPage: true,
    articleId: 0,
    passedSectionId: null,
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
                isReceived: false,
                passedSectionId: null,
            }
        case PASS_SECTION_ID:
            return {
                ...state,
                passedSectionId: action.passedSectionId,
            }
        default:
            return state
    }
}

export class clearSectionId {
}