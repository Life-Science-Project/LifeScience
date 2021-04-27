import {storageUrl} from "../constants"
import {applyMiddleware} from "redux";
import thunkMiddleware from "redux-thunk";

export class StateLoader {

    loadState() {
        try {
            let serializedState = localStorage.getItem(storageUrl);

            if (serializedState === null) {
                return this.initializeState();
            }

            return JSON.parse(serializedState);
        } catch (err) {
            return this.initializeState();
        }
    }

    saveState(state) {
        try {
            let serializedState = JSON.stringify(state);
            localStorage.setItem(storageUrl, serializedState);

        } catch (err) {
        }
    }

    initializeState() {
        return applyMiddleware(thunkMiddleware);
    };
}
