import {CLEAR_PROTOCOLS, RECEIVE_PROTOCOLS} from "./actions/protocol-list-actions";

const initialState = {
    protocols: [],
    isReceived: false,
}

export default function protocolListReducer(state = initialState, action) {
    switch (action.type) {
        case RECEIVE_PROTOCOLS:
            return {
                ...state,
                protocols: action.protocols,
                isReceived: true,
            }
        case CLEAR_PROTOCOLS:
            return {
                ...state,
                isReceived: false,
            }
        default: return state
    }
}