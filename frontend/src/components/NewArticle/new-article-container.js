import "./NewArticle.css"
import React, {useEffect} from "react";
import {useHistory, withRouter} from "react-router-dom";
import {getCategoryThunk} from "../../redux/category-reducer";
import {useDispatch, useSelector} from "react-redux";
import {addMethodThunk, clearPostStatus, PostStatusEnum} from "../../redux/actions/new-article-actions";
import {useRouteMatch} from "react-router";
import Preloader from "../common/Preloader/preloader";
import {LOGIN_URL, METHOD_URL} from "../../constants";
import NewArticleView from "./new-article-view";

const NewArticleContainer = () => {
    const history = useHistory()
    const dispatch = useDispatch()
    const match = useRouteMatch()

    const categoryId = match.params.categoryId;
    const isAuthorized = useSelector(state => state.auth.isAuthorized);
    const isInitialized = useSelector(state => state.auth.isInitialized);
    const category = useSelector(state => state.categoryPage.category)
    const postStatus = useSelector(state => state.newArticle.postStatus)
    const versionId = useSelector(state => state.newArticle.versionId);

    if (!isAuthorized && isInitialized) {
        history.push(LOGIN_URL);
    }

    useEffect(() => {
        refreshCategory()
    }, [match.params])

    const refreshCategory = () => {
        dispatch(getCategoryThunk(categoryId));
    }

    const onSubmit = (sections, methodName) => {
        dispatch(addMethodThunk(categoryId, methodName, sections));
    }

    if (postStatus === PostStatusEnum.POSTING) return <Preloader/>
    if (postStatus === PostStatusEnum.POSTED) {
        dispatch(clearPostStatus()) //clear information for further use
        history.push(`${METHOD_URL}/${versionId}`);
    }

    return <NewArticleView category={category} onSubmit={onSubmit}/>
}

export default withRouter(NewArticleContainer);
