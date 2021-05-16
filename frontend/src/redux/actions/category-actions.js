import {categoryApi} from "../../api/category-api";

export const EMPTY = 'empty';
export const GET_CATEGORY = 'GET_CATEGORY';
export const ERROR = 'ERROR';
export const CLEAR_CATEGORY = "CLEAR_CATEGORY"

export const getCategory = (_id) => {
    return {type: GET_CATEGORY, category: _id}
}


export const getError = (reason, error) => {
    return {type: reason, error: error}
}

export const clearCategory = () => {
    return {
        type: CLEAR_CATEGORY,
    }
}

export const getCategoryThunk = (id) => async (dispatch) => {
    const response = await categoryApi.getCategory(id);
    if (response.status !== 200) {
        dispatch(getError(ERROR, response))
        return;
    }
    dispatch(getCategory(response.data))
}

/**
 * Adding category in db.
 *
 * @param data - contains category information [name, parentId, order].
 * @returns {(function(*): Promise<void>)|*}
 */
export const postCategoryThunk = (data) => async (dispatch) => {
    const response = await categoryApi.postCategory(data);

    if (response.status !== 200) {
        dispatch(getError(ERROR, response))
        return;
    }

    dispatch(getCategory(response.data))
}

/**
 * Delete category from db.
 *
 * @param id - deleting category id.
 * @returns {(function(*): Promise<void>)|*}
 */
export const deleteCategoryThunk = (id) => async (dispatch) => {
    const response = await categoryApi.deleteCategory(id);

    if (response.status !== 200) {
        dispatch(getError(ERROR, response))
    }
}

/**
 * Updates existing category.
 *
 * @param id - updating category id.
 * @param data - contains  category information [name, parentId, order].
 * @returns {(function(*): Promise<void>)|*}
 */
export const putCategoryThunk = (id, data) => async (dispatch) => {
    const response = await categoryApi.putCategory(id, data);

    if (response.status !== 200) {
        dispatch(getError(ERROR, response))
        return;
    }

    dispatch(getCategory(response.data))
}
