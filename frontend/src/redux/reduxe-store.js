import {applyMiddleware, combineReducers, createStore} from "redux";
import thunkMiddleware from 'redux-thunk';
import categoryReducer from "./category-reducer";
import authReducer from "./auth-reducer";
import methodReducer from "./method-reducer";
import sectionReducer from "./section-reducer";
import usersReducer from "./users-reducer";
import initReducer from "./init-reducer";
import searchReducer from "./search-reducer";
import newArticleReducer from "./new-article-reducer";
import protocolListReducer from "./protocol-list-reducer";
import articleReducer from "./article-reducer";
import protocolReducer from "./new-protocol-reducer";

let rootReducer = combineReducers({
    categoryPage: categoryReducer,
    article: articleReducer,
    protocol: protocolReducer,
    usersPage: usersReducer,
    auth: authReducer,
    method: methodReducer,
    section: sectionReducer,
    init: initReducer,
    search: searchReducer,
    newArticle: newArticleReducer,
    protocolList: protocolListReducer,
});

//Вносим свои reducers в rootReducer

let store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

export default store;
