import {methodApi} from "../api/method-api";

const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'
const CLEAR_SECTIONS = 'CLEAR_SECTIONS'
const RECEIVE_PROTOCOLS = 'RECEIVE_PROTOCOLS'

const initialState = {
    name: "",
    sections: [],
    protocols: [],
    isReceived: false,
    isMainPage: true,
}

function receiveSections(data) {
    return {
        type: RECEIVE_SECTIONS,
        sections: data.sections,
        name: data.articleName,
        isMainPage: data.protocolId === null,
    }
}

export function clearSections() {
    return {
        type: CLEAR_SECTIONS
    }
}

function receiveProtocols(data) {
    return {
        type: RECEIVE_PROTOCOLS,
        protocols: data.protocols
    }
}

function fetchProtocols(articleId) {
    return dispatch => {
        return methodApi.getProtocols(articleId)
            .then(response => response.data)
            .then(data => dispatch(receiveProtocols(data)))
    }
}

export function fetchSections(versionId) {
    return dispatch => {
        return methodApi.getMethod(versionId)
            .then(response => response.data)
            .then(data => {
                if (data.protocolId === null) {

                }
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
                isReceived: true,
            }
        case CLEAR_SECTIONS:
            return  {
                ...state,
                isReceived: false
            }
        case RECEIVE_PROTOCOLS:
            return {
                ...state,
                //todo move to sectioncontainer (maybe), decide how to show protocols section when it doesn't have a link
            }
        default:
            return state
    }
}