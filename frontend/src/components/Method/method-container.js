import React, {useEffect, useState} from "react";
import {useRouteMatch, withRouter} from "react-router-dom";
import {connect, useDispatch, useSelector} from 'react-redux';
import {fetchSections} from "../../redux/method-reducer";
import Method from "./method";
import Preloader from "../common/Preloader/preloader";
import AddButton from "./AddButton/addButton";


const MethodContainer = () => {

    const [articleId, setArticleId] = useState(1);
    const match = useRouteMatch()
    const dispatch = useDispatch()

    const name = useSelector(state => state.method.name)
    const sections = useSelector(state => state.method.sections)
    const versionId = useSelector(state => state.method.versionId)
    const isReceived = useSelector(state => state.method.isReceived)
    const isAuthorized = useSelector(state => state.auth.isAuthorized)

    const getSections = () => {
        const id = match.params.articleId;
        setArticleId(id)
        dispatch(fetchSections(id))
    }

    useEffect(() => {
        getSections()
    }, [match.params])

    const getAddButton = () => {
        return isAuthorized && <AddButton articleId={articleId}/>
    }


    if (!isReceived) return <Preloader/>
    return (
        <div>
            <Method name={name} sections={sections} versionId={versionId} addButton={getAddButton()}/>
        </div>
    );

}

export default withRouter(MethodContainer)