import {getAuthorizedUserThunk} from "./auth-reducer";
import {getTokens} from "../utils/auth";
import {initApi} from "../api/init-api";
import {statisticsApi} from "../api/statistics-api";
import {categoryApi} from "../api/category-api";

const INIT = 'init';
const STATISTICS = 'statistics';

const initialState = {
    isInitialized: false,
    initData: null,
    statistics: {userCount: 0},
    isStatisticsInitialized: false
}

const initReducer = (state = initialState, action) => {
    switch (action.type) {
        case INIT:
            return {
                ...state,
                isInitialized: true,
                initData: action.initData
            };
        case STATISTICS:
            return {
                ...state,
                statistics: action.statistics,
                isStatisticsInitialized: true
            }
        default:
            return state;
    }
}

export const getInitData = (initData) => {
    return {type: INIT, initData};
}

export const getInitDataThunk = () => async (dispatch) => {
    const tokens = getTokens();
    if (tokens.jwt !== null) {
        dispatch(getAuthorizedUserThunk());
    } else {
        dispatch(getInitData())
    }
}

export const getStatistics = (statistics) => {
    return {type: STATISTICS, statistics}
}

export const getStatisticsThunk = () => async (dispatch) => {
    let users = await statisticsApi.getUsers();

    let postCount = 0;
    function inc(n) {
        postCount += n;
    }
    await postDFS(1, inc);

    dispatch(getStatistics({
        userCount: users.data.length,
        postCount
    }));
}

async function postDFS(id, inc) {
    let category = await categoryApi.getCategory(id);
    inc(category.data.articles.length);

    for (let subCategory of category.data.subcategories) {
        await postDFS(subCategory.id, inc);
    }
}

export default initReducer;