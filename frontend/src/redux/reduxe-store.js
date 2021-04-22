import {applyMiddleware, combineReducers, createStore} from "redux";
import thunkMiddleware from 'redux-thunk';
import categoryReducer from "./category-rerducer";
import authReducer from "./auth-reducer";

let rootReducer = combineReducers({
    categoryPage: categoryReducer,
    auth: authReducer
});

//Вносим свои reducers в rootReducer

let store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

export default store;