import {articleApi, contentApi, methodApi} from "../api/method-api";
import {categoryApi} from "../api/category-api";
import {getCategory, getError} from "./category-reducer";

const RECEIVE_SECTIONS = 'RECEIVE_SECTIONS'
const REQUEST_SECTIONS = 'REQUEST_SECTIONS'

const initialState = {
    name: "",
    sections: [],
    versionId: 1,
    isReceived: false
}

function receiveSections(data) {
    return {
        type: RECEIVE_SECTIONS,
        sections: data.version.sections,
        name: data.version.name,
        versionId: data.id
    }
}

function requestSections() {
    return {
        type: REQUEST_SECTIONS
    }
}

export function fetchSections(articleId) {
    return dispatch => {
        dispatch(requestSections())
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
                versionId: action.versionId,
                isReceived: true,
            }
        case REQUEST_SECTIONS:
            return  {
                ...state,
                isReceived: false
            }
        default:
            return state
    }
}

export const addMethodThunk = (categoryid, name, sections) => async (dispatch) => {
    let response = await articleApi.postVersion(categoryid, name);
    if (response.status === 200) {
        let versionId = response.data.id;
        for (const s of sections) {
            response = await articleApi.postSection(versionId, s.name);
            if (response.status === 200) {
                let sectionId = response.data.id;
                if (s.content) response = await contentApi.postContent(sectionId, s.content);
            }
        }
        if (response.status === 200) {
           await articleApi.approve(versionId);
        }
    }
}