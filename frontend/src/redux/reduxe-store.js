import {applyMiddleware, combineReducers, createStore} from "redux";
import thunkMiddleware from 'redux-thunk';
import categoryReducer from "./category-rerducer";
import authReducer from "./auth-reducer";
import usersReducer from "./users-reducer";
import {StateLoader} from "./state-loader";

let rootReducer = combineReducers({
    categoryPage: categoryReducer,
    usersPage: usersReducer,
    auth: authReducer
});

//Вносим свои reducers в rootReducer

const stateLoader = new StateLoader();

let store = createStore(rootReducer, stateLoader.loadState());

store.subscribe(() => {
    stateLoader.saveState(store.getState());
});

export default store;