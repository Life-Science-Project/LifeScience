import {applyMiddleware, combineReducers, createStore} from "redux";
import thunkMiddleware from 'redux-thunk';
import categoryReducer from "./category-rerducer";
import authReducer from "./auth-reducer";
import methodReducer from "./method-reducer";
import sectionReducer from "./section-reducer";
import usersReducer from "./users-reducer";
import {StateLoader} from "./state-loader";

let rootReducer = combineReducers({
    categoryPage: categoryReducer,
    auth: authReducer,
    method: methodReducer,
    section: sectionReducer
});

//Вносим свои reducers в rootReducer

const stateLoader = new StateLoader();

let store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

store.subscribe(() => {
    stateLoader.saveState(store.getState());
});

export default store;