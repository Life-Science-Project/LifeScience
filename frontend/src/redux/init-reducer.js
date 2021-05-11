import {getAuthorizedUserThunk} from "./auth-reducer";
import {getTokens} from "../utils/auth";
import {initApi} from "../api/init-api";
import {statisticsApi} from "../api/statistics-api";
import {categoryApi} from "../api/category-api";
import {organizationsApi} from "../api/organizations-api";

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
    let userStatistics = await statisticsApi.getUserStatistics()
    let articleStatistics = await statisticsApi.getArticleStatistics()
    let organizationsStatistics = await statisticsApi.getOrganizationsStatistics()

    dispatch(getStatistics({
        userCount: userStatistics.data,
        postCount: articleStatistics.data,
        organizationsCount: organizationsStatistics.data
    }));
}

export default initReducer;