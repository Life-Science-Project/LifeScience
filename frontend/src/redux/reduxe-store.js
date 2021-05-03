import {applyMiddleware, combineReducers, createStore} from "redux";
import thunkMiddleware from 'redux-thunk';
import categoryReducer from "./category-reducer";
import authReducer from "./auth-reducer";
import methodReducer from "./method-reducer";
import sectionReducer from "./section-reducer";
import usersReducer from "./users-reducer";
import initReducer from "./init-reducer";
import searchReducer from "./search-reducer";

let rootReducer = combineReducers({
    categoryPage: categoryReducer,
    userPage: usersReducer,
    auth: authReducer,
    method: methodReducer,
    section: sectionReducer,
    init: initReducer,
    search: searchReducer,
});

//Вносим свои reducers в rootReducer

let store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

export default store;
