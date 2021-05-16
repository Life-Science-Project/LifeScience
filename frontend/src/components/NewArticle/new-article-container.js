import "./NewArticle.css"
import React, {useEffect} from "react";
import {useHistory, withRouter} from "react-router-dom";
import {getCategoryThunk} from "../../redux/actions/category-actions";
import {useDispatch, useSelector} from "react-redux";
import {addArticleThunk, clearPostStatus, PostStatusEnum} from "../../redux/actions/new-article-actions";
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

    const SECTION_TITLES = ["General Information", "Protocol", "Equipment and reagents required", "Application",
        "Method advantages and disadvantages", "Troubleshooting"];

    const AUTO_SECTION_TITLES = ["Find collaboration", "Education"];

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
        dispatch(addArticleThunk(categoryId, methodName, sections));
    }

    if (postStatus === PostStatusEnum.POSTING) return <Preloader/>
    if (postStatus === PostStatusEnum.POSTED) {
        dispatch(clearPostStatus()) //clear information for further use
        history.push(`${METHOD_URL}/${versionId}`);
    }

    return <NewArticleView category={category} onSubmit={onSubmit} sectionTitles={SECTION_TITLES} autoSectionTitles={AUTO_SECTION_TITLES}/>
}

export default withRouter(NewArticleContainer);
