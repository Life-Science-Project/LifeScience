import "../NewArticle/NewArticle.css"
import React, {useEffect} from "react";
import {useHistory, withRouter} from "react-router-dom";
import {getCategoryThunk} from "../../redux/category-reducer";
import {useDispatch, useSelector} from "react-redux";
import {addMethodThunk, clearPostStatus, PostStatusEnum} from "../../redux/actions/new-article-actions";
import {useRouteMatch} from "react-router";
import Preloader from "../common/Preloader/preloader";
import {LOGIN_URL, METHOD_URL} from "../../constants";
import NewArticleView from "../NewArticle/new-article-view";
import {getArticleThunk} from "../../redux/article-reducer";
import {addProtocolThunk} from "../../redux/new-protocol-reducer";

const NewProtocolContainer = () => {
    const history = useHistory()
    const dispatch = useDispatch()
    const match = useRouteMatch()

    const articleId = match.params.articleId;
    const isAuthorized = useSelector(state => state.auth.isAuthorized);
    const isInitialized = useSelector(state => state.auth.isInitialized);
    const article = useSelector(state => state.article.article)
    const postStatus = useSelector(state => state.protocol.postStatus)
    const versionId = useSelector(state => state.protocol.versionId);

    const SECTION_TITLES = ["Protocol"];

    if (!isAuthorized && isInitialized) {
        history.push(LOGIN_URL);
    }

    useEffect(() => {
        refreshArticle()
    }, [match.params])

    const refreshArticle = () => {
        dispatch(getArticleThunk(articleId));
    }

    const onSubmit = (sections, name) => {
        dispatch(addProtocolThunk(articleId, name, sections));
    }

    if (postStatus === PostStatusEnum.POSTING) return <Preloader/>
    if (postStatus === PostStatusEnum.POSTED) {
        dispatch(clearPostStatus()) //clear information for further use
        history.push(`${METHOD_URL}/${versionId}`);
    }

    return (<NewArticleView article={article} onSubmit={onSubmit} sectionTitles={SECTION_TITLES} autoSectionTitles={[]}/>);
}

export default withRouter(NewProtocolContainer);
